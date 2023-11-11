package jvm.tech.demonlee.gc.finalize;

import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-11-11 21:41
 */
public class FinalizeEscapeGC {

    private static FinalizeEscapeGC saveHook = null;

    public void isAlive() {
        System.out.println("Yes, I am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("oh, finalize method is executed.");
        FinalizeEscapeGC.saveHook = this;
    }

    public static void main(String[] args) throws InterruptedException {
        saveHook = new FinalizeEscapeGC();
        testFinalize();
        testFinalize();
    }

    private static void testFinalize() throws InterruptedException {
        System.out.println("-----------in testFinalize");
        saveHook = null;
        System.gc();
        Thread.sleep(500);
        if (Objects.nonNull(saveHook)) {
            saveHook.isAlive();
        } else {
            System.out.println("No, I am dead :(");
        }
    }
}
