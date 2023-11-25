package jvm.tech.demonlee.gc.reference;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
class SoftReferenceUsageTest {

    private final SoftReferenceUsage softReferenceUsage = new SoftReferenceUsage();

    @Test
    void outOfMemoryError4HardReference() {
        softReferenceUsage.outOfMemoryError4HardReference();
    }

    @Test
    void outOfMemoryError4SoftReference() {
        softReferenceUsage.outOfMemoryError();
    }
}
