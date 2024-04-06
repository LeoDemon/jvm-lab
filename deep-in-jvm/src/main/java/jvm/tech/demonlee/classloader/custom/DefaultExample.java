package jvm.tech.demonlee.classloader.custom;

import lombok.extern.log4j.Log4j2;

/**
 * @author Demon.Lee
 * @date 2023-12-19 08:08
 */
@Log4j2
public class DefaultExample {

    public static void main(String[] args) {
        printDefaultClassLoader();
    }

    private static void printDefaultClassLoader() {
        log.info("system config: {}", System.getProperty("java.system.class.loader"));
        log.info("current class: {}, {}", DefaultExample.class.getClassLoader(),
                DefaultExample.class.getClassLoader().getName());

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader systemParentClassLoader = systemClassLoader.getParent();
        ClassLoader systemGrandClassLoader = systemClassLoader.getParent().getParent();
        log.info("system default: {}, {}", systemClassLoader, systemClassLoader.getName());
        log.info("system default parent: {}, {}", systemParentClassLoader, systemParentClassLoader.getName());
        log.info("system default grand: {}", systemGrandClassLoader);
    }
}
