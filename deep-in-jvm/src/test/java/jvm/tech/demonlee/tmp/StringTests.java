package jvm.tech.demonlee.tmp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

/**
 * @author Demon.Lee
 * @date 2023-12-08 11:58
 */
@Log4j2
public class StringTests {

    @Test
    void stringCompare() {
        String str1 = "abc";
        String str11 = "abc";
        String str2 = new String("abc");
        String str3 = str2.intern();

        log.info("str1: {}, {}", str1.hashCode(), System.identityHashCode(str1));
        log.info("str11: {}, {}", str11.hashCode(), System.identityHashCode(str11));
        log.info("str2: {}, {}", str2.hashCode(), System.identityHashCode(str2));
        log.info("str3: {}, {}", str3.hashCode(), System.identityHashCode(str3));

        Assertions.assertTrue(str1 == str11);
        Assertions.assertFalse(str1 == str2);
        Assertions.assertFalse(str2 == str3);
        Assertions.assertTrue(str1 == str3);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        int[] a = new int[]{1, 2, 3};
        String className = a.getClass().getName();
        System.out.println(className);
        System.out.println(a.getClass().getCanonicalName());
        Class<?> clazz = Class.forName(className);
        System.out.println("en..."+clazz.getName());
    }
}
