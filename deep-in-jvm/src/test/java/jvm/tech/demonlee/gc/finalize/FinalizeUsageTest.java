package jvm.tech.demonlee.gc.finalize;

import jvm.tech.demonlee.common.model.Student;
import jvm.tech.demonlee.common.model.StudentEx;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Demon.Lee
 * @date 2023-11-30 08:22
 */
@Log4j2
public class FinalizeUsageTest {

    @SneakyThrows
    @Test
    void finalizeOnce() {
        Student stu = new StudentEx("10001", "Jack");
        stu = null;

        log.info("Suggest the garbage collector to initiate a collection...");
        System.gc();
        TimeUnit.SECONDS.sleep(3);
    }

    // -Xlog:gc:time
    @SneakyThrows
    @Test
    void finalizeMemory() {
        Student stu = new StudentEx("10001", "Jack", new byte[1024 * 1024 * 200]);
        stu = null;

        log.info("Suggest the garbage collector to initiate a collection...");
        System.gc();

        TimeUnit.SECONDS.sleep(30);
        log.info("test exit now...");
    }

    // -Xlog:gc:time
    @SneakyThrows
    @Test
    void finalizeMemoryWithGCAgain() {
        Student stu = new StudentEx("10001", "Jack", new byte[1024 * 1024 * 200]);
        stu = null;

        log.info("Suggest the garbage collector to initiate a collection...");
        System.gc();
        TimeUnit.SECONDS.sleep(15);

        log.info("Suggest the garbage collector to initiate a collection again...");
        System.gc();

        TimeUnit.SECONDS.sleep(15);

        log.info("test exit now...");
    }
}
