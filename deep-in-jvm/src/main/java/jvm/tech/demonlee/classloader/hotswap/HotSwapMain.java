package jvm.tech.demonlee.classloader.hotswap;

import jvm.tech.demonlee.common.model.Student;
import jvm.tech.demonlee.common.util.CompilerUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Demon.Lee
 * @date 2023-12-15 14:07
 */
@Log4j2
public class HotSwapMain {

    private final AtomicBoolean fileChanged = new AtomicBoolean(false);

    private static final String FILE_PATH_PREFIX = "/Users/ispace/coding/coding_for_study" +
            "/coding_for_java/jvm-lab/deep-in-jvm";

    public static void main(String[] args) throws Exception {
        HotSwapMain runner = new HotSwapMain();
        Thread thread1 = new Thread(runner::loadChangedFile);
        thread1.start();
        thread1.join();
    }

    @SneakyThrows
    private void loadChangedFile() {
        String javaFilePath = FILE_PATH_PREFIX + "/src/main/java/jvm/tech/demonlee/common/model/Student.java";
        String classPath = FILE_PATH_PREFIX + "/build/classes/java/main";
        String classFilePath = classPath + "/jvm/tech/demonlee/common/model/Student.class";
        String className = "jvm.tech.demonlee.common.model.Student";

        // load class by `app` ClassLoader
        log.info("student init: {}", new Student());

        FileDigest fileDigest = new FileDigest(javaFilePath);
        while (true) {
            if (fileDigest.isNotChanged()) {
                TimeUnit.SECONDS.sleep(5);
                continue;
            }

            if (!CompilerUtil.compile(javaFilePath, classPath)) {
                log.error("Compilation failed.");
                return;
            }

            loadClass(className, classFilePath);

            fileChanged.compareAndSet(false, true);
        }
    }

    private void loadClass(String className, String classFilePath) throws Exception {
        HotSwapClassLoader classLoader = new HotSwapClassLoader();
        Class<?> clazz = classLoader.loadClass(className, classFilePath);
        log.info("load class success: {}", clazz.getCanonicalName());

        Object instance = clazz.getDeclaredConstructor().newInstance();
        log.info("student from {}: {}", classLoader.getName(), instance);

        ClassLoader classLoader2 = this.getClass().getClassLoader();
        Class<?> clazz2 = classLoader2.loadClass(className);
        Object instance2 = clazz2.getDeclaredConstructor().newInstance();
        log.info("student from {}: {}", classLoader2.getName(), instance2);

        log.info("student new: {}", new Student());
    }
}
