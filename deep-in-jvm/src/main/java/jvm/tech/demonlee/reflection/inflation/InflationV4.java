package jvm.tech.demonlee.reflection.inflation;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Demon.Lee
 * @date 2023-08-08 21:40
 */
public class InflationV4 {

    public static void target(int i) {
    }

    public static void testTarget() throws Exception {
        Class<?> clazz = Class.forName(InflationV4.class.getName());
        Method method = clazz.getMethod("target", int.class);
        // 1.close access auth check
        method.setAccessible(true);

        long[] costs = new long[20];
        long currentTime = System.currentTimeMillis();
        for (int i = 1, j = 0; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long tmpTime = System.currentTimeMillis();
                long cost = tmpTime - currentTime;
                costs[j++] = cost;
                currentTime = tmpTime;
            }

            // 2. -Dsun.reflect.noInflation=true
            // 3. -Djava.lang.Integer.IntegerCache.high=128
            method.invoke(null, 128); // 102 ms, 1.5x
        }
        displayCosts(costs);
    }

    private static void displayCosts(long[] costs) {
        System.out.println("costs: " + Arrays.toString(costs));
        long lastFiveTotal = 0L;
        for (int j = 15; j < 20; j++) {
            lastFiveTotal += costs[j];
        }
        System.out.println("avg cost: " + lastFiveTotal / 5);
    }

    public static void main(String[] args) throws Exception {
        testTarget();
    }
}
