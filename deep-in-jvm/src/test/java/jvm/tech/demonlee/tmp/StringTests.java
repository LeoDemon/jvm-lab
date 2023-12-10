package jvm.tech.demonlee.tmp;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Demon.Lee
 * @date 2023-12-08 11:58
 */
@Log4j2
public class StringTests {

    @Test
    void stringCompare() {
        String str1 = "abc";
        String str2 = new String("abc");
        String str3 = str2.intern();

        log.info("str1: {}, {}", str1.hashCode(), System.identityHashCode(str1));
        log.info("str2: {}, {}", str2.hashCode(), System.identityHashCode(str2));
        log.info("str3: {}, {}", str3.hashCode(), System.identityHashCode(str3));

        Assertions.assertFalse(str1 == str2);
        Assertions.assertFalse(str2 == str3);
        Assertions.assertTrue(str1 == str3);
    }
}
