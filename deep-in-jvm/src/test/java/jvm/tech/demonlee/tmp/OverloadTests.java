package jvm.tech.demonlee.tmp;

import java.io.Serializable;

/**
 * @author Demon.Lee
 * @date 2024-10-06 16:20
 */
public class OverloadTests {

    // ⑥
    public static void sayHello(Object arg) {
        System.out.println("hello Object");
    }

    // ②
    public static void sayHello(int arg) {
        System.out.println("hello int");
    }

    // ③
    public static void sayHello(long arg) {
        System.out.println("hello long");
    }

    // ④
    public static void sayHello(Character arg) {
        System.out.println("hello Character");
    }

    // ①
    public static void sayHello(char arg) {
        System.out.println("hello char");
    }

    // ⑦
    public static void sayHello(char... arg) {
        System.out.println("hello char ...");
    }

    // ⑤
    public static void sayHello(Serializable arg) {
        System.out.println("hello Serializable");
    }

    public static void main(String[] args) {
        sayHello('a');
    }
}
