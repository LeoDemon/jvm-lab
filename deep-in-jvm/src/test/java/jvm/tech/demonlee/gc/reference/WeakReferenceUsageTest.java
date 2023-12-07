package jvm.tech.demonlee.gc.reference;

import jvm.tech.demonlee.common.model.Student;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-12-04 11:16
 */
@Log4j2
public class WeakReferenceUsageTest {

    @Test
    void usage() {
        WeakReference<Student> weakRef = new WeakReference<>(new Student("10001", "LiLei"));
        usage(weakRef);
    }

    @Test
    void usageWithReferenceQueue() throws InterruptedException {
        ReferenceQueue<Student> referenceQueue = new ReferenceQueue<>();
        WeakReference<Student> weakRef = new WeakReference<>(new Student("10001", "LiLei"), referenceQueue);
        usage(weakRef);
        usageWithReferenceQueue(referenceQueue);
    }

    private void usageWithReferenceQueue(ReferenceQueue<Student> referenceQueue) throws InterruptedException {
        Reference<? extends Student> queueRef = referenceQueue.remove(3000);
        log.info("get ref from queue: {}", queueRef);
        if (Objects.nonNull(queueRef)) {
            log.info("get ref.stu from queue: {}", queueRef.get());
        }
    }

    private void usage(WeakReference<Student> weakRef) {
        WeakReferenceUsage weakReferenceUsage = new WeakReferenceUsage();
        weakReferenceUsage.checkExists(weakRef);

        log.info("Suggest the garbage collector to initiate a collection...");
        System.gc();

        weakReferenceUsage.checkExists(weakRef);
    }

    @Test
    void usageForMappingClear() {
        WeakReferenceUsage weakReferenceUsage = new WeakReferenceUsage();
        weakReferenceUsage.usage();
    }
}
