package jvm.tech.demonlee.gc.reference;

import jvm.tech.demonlee.common.model.Student;
import jvm.tech.demonlee.common.model.StudentEx;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
class PhantomReferenceUsageTest {

    private final PhantomReferenceUsage phantomReferenceUsage = new PhantomReferenceUsage();

    @Test
    void testRefersTo() {
        phantomReferenceUsage.refersTo(new Student("10001", "LiLei"));
    }

    @Test
    void usageByIsEnqueued() {
        phantomReferenceUsage.isEnqueued(new Student("10001", "LiLei"));
    }

    @Test
    void usage() {
        phantomReferenceUsage.usageViaNotify(new Student("10001", "LiLei"));
    }

    @Test
    void usageByOverrideFinalize() {
        phantomReferenceUsage.usageViaNotify(new StudentEx("10001", "LiLei"));
    }

    @Test
    void usageByGCAgain() {
        phantomReferenceUsage.usageViaNotifyAndGCAgain(new Student("10001", "LiLei"));
    }

    @Test
    void usageByOverrideFinalizeAndGCAgain() {
        phantomReferenceUsage.usageViaNotifyAndGCAgain(new StudentEx("10001", "LiLei"));
    }

    // -Xlog:gc:time -Xlog:gc*:time
    @Test
    void checkGC() {
        phantomReferenceUsage.checkGC();
    }
}