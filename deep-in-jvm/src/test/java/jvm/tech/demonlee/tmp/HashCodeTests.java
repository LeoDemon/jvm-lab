package jvm.tech.demonlee.tmp;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
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
        private String value;

        public CodeDemoEx(String name) {
            this.name = name;
        }

        public CodeDemoEx(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "CodeDemoEx{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CodeDemoEx that)) return false;
            return Objects.equals(name, that.name) && Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, value);
        }
    }

    @Test
    void hashMapKeyTest() {
        Map<CodeDemoEx, String> map = new HashMap<>();
        map.put(new CodeDemoEx("Jack.Ma"), "111");
        map.put(new CodeDemoEx("王子奇"), "123");
        log.info("Ex: {}", map.get(new CodeDemoEx("Jack.Ma")));
        log.info("Ex2: {}", map.get(new CodeDemoEx("王子奇")));

        log.info("demo1.hashCode: {}", new CodeDemoEx("Jack.Ma").hashCode());
        log.info("demo2.hashCode: {}", new CodeDemoEx("Jack.Ma").hashCode());
    }
}
