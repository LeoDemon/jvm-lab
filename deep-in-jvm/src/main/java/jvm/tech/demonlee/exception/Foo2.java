package jvm.tech.demonlee.exception;

/**
 * @author Demon.Lee
 * @date 2023-09-11 08:29
 * @desc for try-with-resources syntactic sugar
 */
public class Foo2 implements AutoCloseable {

    private final String name;

    public Foo2(String name) {
        this.name = name;
    }

    @Override
    public void close() {
        throw new RuntimeException(this.name);
    }

    public static void main(String[] args) {
        try (Foo2 foo0 = new Foo2("Foo0");
             Foo2 foo1 = new Foo2("Foo1");
             Foo2 foo2 = new Foo2("Foo2")) {
            throw new RuntimeException("Exception from main...");
        }
    }
}

/*
 * output:
 *
 * Exception in thread "main" java.lang.RuntimeException: Exception from main...
 * 	at jvm.tech.demonlee.exception.Foo2.main(Foo2.java:25)
 * 	Suppressed: java.lang.RuntimeException: Foo2
 * 		at jvm.tech.demonlee.exception.Foo2.close(Foo2.java:18)
 * 		at jvm.tech.demonlee.exception.Foo2.main(Foo2.java:22)
 * 	Suppressed: java.lang.RuntimeException: Foo1
 * 		at jvm.tech.demonlee.exception.Foo2.close(Foo2.java:18)
 * 		at jvm.tech.demonlee.exception.Foo2.main(Foo2.java:22)
 * 	Suppressed: java.lang.RuntimeException: Foo0
 * 		at jvm.tech.demonlee.exception.Foo2.close(Foo2.java:18)
 * 		at jvm.tech.demonlee.exception.Foo2.main(Foo2.java:22)
 */
