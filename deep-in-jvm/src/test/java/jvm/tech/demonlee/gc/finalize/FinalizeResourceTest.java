package jvm.tech.demonlee.gc.finalize;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Demon.Lee
 * @date 2023-12-01 11:47
 */
@Log4j2
public class FinalizeResourceTest {

    @Test
    void tryWithResourcesUsage() throws InterruptedException {
        try (CleanerExample cleanerExample = new CleanerExample()) {
            cleanerExample.execute();
        }
    }

    @Test
    void cleanerUsage() throws InterruptedException {
        CleanerExample cleanerExample = new CleanerExample();
        cleanerExample.execute();

        cleanerExample = null;
        System.gc();
        TimeUnit.SECONDS.sleep(1);
    }
}
