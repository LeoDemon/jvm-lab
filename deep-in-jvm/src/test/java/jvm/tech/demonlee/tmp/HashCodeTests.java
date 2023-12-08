package jvm.tech.demonlee.tmp;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-12-08 14:07
 */
@Log4j2
public class HashCodeTests {

    @Test
    void stringTest() {
        String str1 = "abc";
        String str2 = new String("abc");
        String str3 = str2.intern();

        log.info("str1: {}, {}", str1.hashCode(), System.identityHashCode(str1));
        log.info("str2: {}, {}", str2.hashCode(), System.identityHashCode(str2));
        log.info("str3: {}, {}", str3.hashCode(), System.identityHashCode(str3));
    }

    @Test
    void objectTest() {
        CodeDemo demo = new CodeDemo("111");
        log.info("demo: {}, {}", demo.hashCode(), System.identityHashCode(demo));
        Assertions.assertEquals(demo.hashCode(), System.identityHashCode(demo));

        CodeDemoEx demoEx = new CodeDemoEx("111");
        log.info("demoEx: {}, {}", demoEx.hashCode(), System.identityHashCode(demoEx));
        Assertions.assertNotEquals(demoEx.hashCode(), System.identityHashCode(demoEx));
    }

    static class CodeDemo {
        private final String name;

        public CodeDemo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static class CodeDemoEx {
        private final String name;

        public CodeDemoEx(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CodeDemoEx that = (CodeDemoEx) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
