package jvm.tech.demonlee.exception;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Demon.Lee
 * @date 2023-09-08 08:53
 */
public class Foo {

    private int tryBlock;
    private int catchBlock;
    private int finallyBlock;
    private int methodExit;

    public void test() {
        try {
            tryBlock = 0;
        } catch (Exception e) {
            catchBlock = 1;
        } finally {
            finallyBlock = 2;
        }
        methodExit = 3;
    }

    public static void main(String[] args) {
        try {
            mayThrowException();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void mayThrowException() {
        int a = ThreadLocalRandom.current().nextInt();
        if (a > 0) {
            throw new RuntimeException("I need a positive number.");
        }
    }
}

/**
 * Java 17
 *
 * $ javap -c tech.demonlee.exception.Foo
 * -------------------------------------
 * public class jvm.tech.demonlee.exception.Foo {
 *   public jvm.tech.demonlee.exception.Foo();
 *     Code:
 *        0: aload_0
 *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *        4: return
 *
 *   public void test();
 *     Code:
 *        0: aload_0
 *        1: iconst_0
 *        2: putfield      #7                  // Field tryBlock:I
 *        5: aload_0
 *        6: iconst_2
 *        7: putfield      #13                 // Field finallyBlock:I
 *       10: goto          35
 *       13: astore_1
 *       14: aload_0
 *       15: iconst_1
 *       16: putfield      #18                 // Field catchBlock:I
 *       19: aload_0
 *       20: iconst_2
 *       21: putfield      #13                 // Field finallyBlock:I
 *       24: goto          35
 *       27: astore_2
 *       28: aload_0
 *       29: iconst_2
 *       30: putfield      #13                 // Field finallyBlock:I
 *       33: aload_2
 *       34: athrow
 *       35: aload_0
 *       36: iconst_3
 *       37: putfield      #21                 // Field methodExit:I
 *       40: return
 *     Exception table:
 *        from    to  target type
 *            0     5    13   Class java/lang/Exception
 *            0     5    27   any
 *           13    19    27   any
 *
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: invokestatic  #24                 // Method mayThrowException:()V
 *        3: goto          11
 *        6: astore_1
 *        7: aload_1
 *        8: invokevirtual #27                 // Method java/lang/Exception.printStackTrace:()V
 *       11: return
 *     Exception table:
 *        from    to  target type
 *            0     3     6   Class java/lang/Exception
 * }
 */