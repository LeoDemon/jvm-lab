package agent;

/**
 * @author Demon.Lee
 * @date 2023-12-15 16:53
 */

import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class HotSwapAgent {

    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    private final Instrumentation instrumentation;
    private final Path classPath;

    /**
     * Creates a WatchService and registers the given directory
     */
    HotSwapAgent(Path dir, Instrumentation instrumentation) throws IOException {
        System.out.println("----monitor dir: " + dir);
        this.classPath = dir;
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<>();
        this.instrumentation = instrumentation;
        registerAll(dir);
    }

    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void processEvents() {
        while (true) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("------get event: " + event.kind() + " for " + event.context());
                // 过滤掉不需要处理的事件
                if (event.kind() == OVERFLOW) {
                    continue;
                }

                // 获取事件所涉及的文件名（相对路径）
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path filename = ev.context();

                // 将相对路径转换为绝对路径
                Path path = keys.get(key);
                Path fullPath = path.resolve(filename);
                if (fullPath.toFile().isDirectory()) {
                    System.out.println("ignore directory: " + fullPath);
                    continue;
                }
                System.out.println("register path is: " + path + ", File changed: " + fullPath);
                if (event.kind() == ENTRY_MODIFY) {
                    updateClass(fullPath, instrumentation);
                    System.setProperty("agentUpdate", "true");
                }
                // Todo: other kinds
            }
            key.reset();
        }
    }

    private void updateClass(Path fullPath, Instrumentation instrumentation) {
        try {
            // 假设 classFilePath 是完整的 .class 文件路径
            String className = extractClassName(fullPath);
            System.out.println("extract className: " + className);
            byte[] classData = Files.readAllBytes(fullPath);

            // 获取当前已加载的类的 Class 对象
            Class<?> targetClass = Class.forName(className);
            System.out.println("load targetClass is: " + targetClass.getCanonicalName());

            // 使用新的类定义来重新定义类
            ClassDefinition classDefinition = new ClassDefinition(targetClass, classData);
            instrumentation.redefineClasses(classDefinition);
            System.out.println(className + ": has been redefined");
        } catch (Exception ex) {
            System.out.println("update class failed: " + ex);
            ex.printStackTrace();
        }
    }

    private String extractClassName(Path fullPath) {
        // 此方法需要从类文件路径提取完整的类名
        Path relativePath = Paths.get(classPath.toAbsolutePath().toString()).relativize(fullPath);
        String className = relativePath.toString();
        className = className.replace(File.separator, ".");
        className = className.substring(0, className.length() - ".class".length());
        return className;
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        try {
            Path path = Paths.get(agentArgs); // agentArgs 应该是要监控的文件路径
            HotSwapAgent agent = new HotSwapAgent(path, instrumentation);
            Thread thread = new Thread(agent::processEvents);
            thread.start();
            thread.join();
        } catch (Exception e) {
            System.out.println("agentmain: " + e);
        }
    }
}
