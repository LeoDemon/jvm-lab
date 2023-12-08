package jvm.tech.demonlee.tmp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * @author Demon.Lee
 * @date 2023-11-29 10:42
 */
public class DurationTests {

    private boolean noNeedRenew(long startedTime, int lockMinutes, int expireThresholdSeconds) {
        Duration pastTime = Duration.ofMillis(System.currentTimeMillis()).minusMillis(startedTime);
        Duration leftThresholdTime = Duration.ofMinutes(lockMinutes).minusSeconds(expireThresholdSeconds);
        return pastTime.compareTo(leftThresholdTime) < 0;
    }

    private boolean noNeedRenew2(long startedTime, int lockMinutes, int expireThresholdSeconds) {
        long pastTimeMillis = System.currentTimeMillis() - startedTime;
        long leftThresholdTimeMillis = lockMinutes * 60 * 1000L - expireThresholdSeconds * 1000L;
        return pastTimeMillis < leftThresholdTimeMillis;
    }

    @Test
    void testDurationCalculation() {
        Assertions.assertTrue(noNeedRenew(System.currentTimeMillis() - 60 * 1000L, 10, 120));
        Assertions.assertTrue(noNeedRenew(System.currentTimeMillis() - 5 * 60 * 1000L, 10, 120));
        Assertions.assertFalse(noNeedRenew(System.currentTimeMillis() - 8 * 60 * 1000L, 10, 120));
        Assertions.assertFalse(noNeedRenew(System.currentTimeMillis() - 11 * 60 * 1000L, 10, 120));
    }

    @Test
    void testManualCalculation() {
        Assertions.assertTrue(noNeedRenew2(System.currentTimeMillis() - 60 * 1000L, 10, 120));
        Assertions.assertTrue(noNeedRenew2(System.currentTimeMillis() - 5 * 60 * 1000L, 10, 120));
        Assertions.assertFalse(noNeedRenew2(System.currentTimeMillis() - 8 * 60 * 1000L, 10, 120));
        Assertions.assertFalse(noNeedRenew2(System.currentTimeMillis() - 11 * 60 * 1000L, 10, 120));
    }
}