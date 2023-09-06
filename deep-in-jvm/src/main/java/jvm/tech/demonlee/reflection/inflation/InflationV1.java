package jvm.tech.demonlee.reflection.inflation;

import java.lang.reflect.Method;

/**
 * @author Demon.Lee
 * @date 2023-08-08 21:30
 */
public class InflationV1 {

    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void testTarget() throws Exception {
        Class<?> clazz = Class.forName(InflationV1.class.getName());
        Method method = clazz.getMethod("target", int.class);
        for (int i = 0; i < 20; i++) {
            method.invoke(null, i);
        }
    }

    public static void main(String[] args) throws Exception {
        testTarget();
    }
}
