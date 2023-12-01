package jvm.tech.demonlee.gc.finalize;

import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-11-11 21:41
 */
@Log4j2
public class FinalizeEscapeGC {

    private static FinalizeEscapeGC saveHook = null;

    public void isAlive() {
        log.info("Yes, I am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        log.info("oh, finalize method is executed.");
        FinalizeEscapeGC.saveHook = this;
    }

    public static void main(String[] args) throws InterruptedException {
        saveHook = new FinalizeEscapeGC();
        testFinalize();
        testFinalize();
    }

    private static void testFinalize() throws InterruptedException {
        log.info("in testFinalize");
        saveHook = null;
        System.gc();
        Thread.sleep(500);
        if (Objects.nonNull(saveHook)) {
            saveHook.isAlive();
        } else {
            log.info("No, I am dead :(");
        }
    }
}
