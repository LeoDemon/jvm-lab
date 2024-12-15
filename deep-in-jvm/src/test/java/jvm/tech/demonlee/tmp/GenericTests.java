package jvm.tech.demonlee.tmp;

import java.util.concurrent.ThreadLocalRandom;

class GenericTests {

    public static void main(String[] args) {
        System.out.println("package: "+ ThreadLocalRandom.class.getPackage().getName());
    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch(ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
            default: throw new AssertionError();
        }
    }

    static <T> T[] toArray(T... args) {
        return args;
    }
}
