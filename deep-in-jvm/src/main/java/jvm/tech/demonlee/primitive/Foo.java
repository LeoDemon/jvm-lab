package jvm.tech.demonlee.primitive;

/**
 * @author Demon.Lee
 * @date 2023-09-20 08:34
 * @desc Test boolean type
 */
public class Foo {

    /**
     * $ cd xxx/jvm-lab/deep-in-jvm/build/classes/java/main
     * $ javac jvm/tech/demonlee/primitive/Foo.java
     * $ java jvm/tech/demonlee/primitive/Foo
     * $ java -cp ./asmtools.jar org.openjdk.asmtools.jdis.Main jvm/tech/demonlee/primitive/Foo.class > jvm/tech/demonlee/primitive/Foo.jasm.1
     * $ awk 'NR==1,/iconst_1/{sub(/iconst_1/, "iconst_2")} 1' jvm/tech/demonlee/primitive/Foo.jasm.1 > jvm/tech/demonlee/primitive/Foo.jasm
     * $ java -cp ./asmtools.jar org.openjdk.asmtools.jasm.Main jvm/tech/demonlee/primitive/Foo.jasm -d .
     * $ java jvm/tech/demonlee/primitive/Foo
     */
    public static void main(String[] args) {
        boolean flag = true;

        if (flag) System.out.println("Hello, Java!");

        if (flag == true) System.out.println("Hello, JVM!");
    }
}