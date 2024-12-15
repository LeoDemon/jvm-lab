package jvm.tech.demonlee.jni;

/**
 * @author Demon.Lee
 * @date 2024-12-15 08:55
 */
public class FooNative {

    public native int bar(int i, String s);

    public static void main(String[] args) {
        try {
            System.loadLibrary("foo_native");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            System.exit(1);
        }
        FooNative fooNative = new FooNative();
        int result = fooNative.bar(6, "世界");
        System.out.println("...........result: " + result);
    }
}
