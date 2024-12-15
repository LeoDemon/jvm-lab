package jvm.tech.demonlee.tmp;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * @author Demon.Lee
 * @date 2024-08-11 16:40
 */
public class MethodHandleTests {

    static class GrandFather {
        void thinking() {
            System.out.println("I am grandfather...");
        }
    }

    static class Father extends GrandFather {
        @Override
        void thinking() {
            System.out.println("I am father...");
        }
    }

    static class Son extends Father {
        @Override
        void thinking() {
            MethodType mt = MethodType.methodType(void.class);
            try {
                // MethodHandles.Lookup lookup = MethodHandles.lookup();
                // MethodHandle mh = lookup.findSpecial(GrandFather.class, "thinking", mt, getClass());
                Field implLookup = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                implLookup.setAccessible(true);
                MethodHandles.Lookup lookup = (MethodHandles.Lookup) implLookup.get(null);
                MethodHandle mh = lookup.findSpecial(GrandFather.class, "thinking", mt, GrandFather.class);
                mh.invoke(this);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Son son = new Son();
        son.thinking();
    }
}
