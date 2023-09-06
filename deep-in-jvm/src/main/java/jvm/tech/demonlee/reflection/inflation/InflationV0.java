package jvm.tech.demonlee.reflection.inflation;

import java.lang.reflect.Method;

/**
 * @author Demon.Lee
 * @date 2023-08-08 21:25
 */
public class InflationV0 {

    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void testTarget() throws Exception {
        Class<?> clazz = Class.forName(InflationV0.class.getName());
        Method method = clazz.getMethod("target", int.class);
        method.invoke(null, 0);
    }

    // 1. close inflation mechanism: -Dsun.reflect.noInflation=true
    // 2. print loaded classes detail: -verbose:class
    public static void main(String[] args) throws Exception {
        System.out.println("noInflation: " + System.getProperty("sun.reflect.noInflation"));
        testTarget();
    }
}
