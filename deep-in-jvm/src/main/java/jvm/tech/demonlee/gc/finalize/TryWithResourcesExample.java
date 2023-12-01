package jvm.tech.demonlee.gc.finalize;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

/**
 * @author Demon.Lee
 * @date 2023-12-01 11:47
 */
@Log4j2
public class TryWithResourcesExample implements AutoCloseable {

    @Override
    public void close() {
        log.info("release some resources now...");
    }

    public void execute() throws InterruptedException {
        log.info("do sth now...");
        TimeUnit.SECONDS.sleep(1);
    }
}
