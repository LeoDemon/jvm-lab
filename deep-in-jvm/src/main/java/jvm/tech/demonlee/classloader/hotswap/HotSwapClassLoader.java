package jvm.tech.demonlee.classloader.hotswap;

/**
 * @author Demon.Lee
 * @date 2023-12-15 14:02
 */

import lombok.extern.log4j.Log4j2;

import java.io.*;

@Log4j2
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super("HotSwap", HotSwapClassLoader.class.getClassLoader());
        log.info("classLoader is: {}", HotSwapClassLoader.class.getClassLoader().getName());
    }

    public Class<?> loadClass(String name, String filePath) throws IOException {
        log.info("load class {} from {}", name, filePath);
        byte[] classData = getClassData(filePath);
        if (classData == null || classData.length == 0) {
            throw new FileNotFoundException("No class data found for " + name);
        }
        return defineClass(name, classData, 0, classData.length);
    }

    private byte[] getClassData(String filePath) throws IOException {
        try (InputStream is = new FileInputStream(filePath);
             ByteArrayOutputStream byteSt = new ByteArrayOutputStream()) {
            int len;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                byteSt.write(buffer, 0, len);
            }
            return byteSt.toByteArray();
        }
    }
}
