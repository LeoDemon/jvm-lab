package jvm.tech.demonlee.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author Demon.Lee
 * @date 2024-05-24 08:50
 */
public class MethodHandlerBaseExample {

    private String name = "Jacky";
    private int score = 20;

    public static int foo(String name, int score) {
        System.out.println("------Hello: " + name);
        return 10 * score;
    }

    public static MethodHandles.Lookup lookup() {
        return MethodHandles.lookup();
    }

    @Override
    public String toString() {
        return "MethodHandlerBaseExample{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandlerBaseExample.lookup();
        invokeStaticMethod(lookup);
        invokeObjectField(lookup);
    }

    private static void invokeObjectField(MethodHandles.Lookup lookup) throws Throwable {
        MethodHandlerBaseExample example = new MethodHandlerBaseExample();
        MethodHandle getterHandle = lookup.findGetter(MethodHandlerBaseExample.class, "score", int.class);
        int score = (int) getterHandle.invokeExact(example);
        System.out.println("------score: " + score);

        MethodHandle setterHandle = lookup.findSetter(MethodHandlerBaseExample.class, "name", String.class);
        setterHandle.invokeExact(example, "蔡崇信");
        System.out.println("------example: " + example);
    }

    private static void invokeStaticMethod(MethodHandles.Lookup lookup) throws Throwable {
        MethodType methodType = MethodType.methodType(int.class, String.class, int.class);
        MethodHandle methodHandle = lookup.findStatic(MethodHandlerBaseExample.class, "foo", methodType);
        int ret = (int) methodHandle.invokeExact("Jack.马", 9);
        System.out.println("------return: " + ret);
    }
}
