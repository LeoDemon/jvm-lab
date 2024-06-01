package jvm.tech.demonlee.tmp;

import jvm.tech.demonlee.tmp.model.FooBar;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author Demon.Lee
 * @date 2024-06-01 10:16
 */
@Log4j2
class CloneTests {

    @Test
    void should_clone() {
        FooBar fooBar = new FooBar("Jack.马", Arrays.asList("牛", "马", "狗"));
        log.info("foo: {}", fooBar);
        FooBar fooBarClone = fooBar.clone();
        log.info("fooBarClone: {}", fooBarClone);

        log.info("fooBar == fooBarClone: {}", fooBar == fooBarClone);
        log.info("fooBar equals fooBarClone: {}", fooBar.equals(fooBarClone));
        log.info("fooBar.class == fooBarClone.class: {}", fooBar.getClass() == fooBarClone.getClass());

        fooBar.removeValue("马");
        fooBarClone.addValue("鼠");
        log.info("fooBar: {}", fooBar);
        log.info("fooBar equals fooBarClone: {}", fooBar.equals(fooBarClone));
    }

    @Test
    void should_clone_array() {
        String a[] = new String[]{"你好", "Hello"};
        String a2[] = a.clone();
        log.info("a2: {}", Arrays.stream(a2).toList());
        a2[1] = "Hi";
        log.info("a: {}", Arrays.stream(a).toList());
        log.info("a2: {}", Arrays.stream(a2).toList());

        int c[][] = new int[][]{
                {0, 3, 2, 9},
                {2, 9, 222, 333}
        };
        int c2[][] = c.clone();
        log.info("c2: {}", Arrays.stream(c2).toList());
        c2[1][1] = -29;
        log.info("c2: {}", Arrays.stream(c2).toList());
        log.info("c: {}", Arrays.stream(c).toList());
    }
}
