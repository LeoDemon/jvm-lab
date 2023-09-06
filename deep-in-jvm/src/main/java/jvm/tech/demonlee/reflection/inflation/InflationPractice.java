package jvm.tech.demonlee.reflection.inflation;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Demon.Lee
 * @date 2023-08-08 21:40
 */
public class InflationPractice {

    public static void target(int i) {
    }

    public static void testTarget() throws Exception {
        Class<?> clazz = Class.forName(InflationPractice.class.getName());
        Method method = clazz.getMethod("target", int.class);
        // 1.close access auth check
        method.setAccessible(true);

        // 2. more profile to interfere with method inline
        polluteProfile();

        long[] costs = new long[20];
        long currentTime = System.currentTimeMillis();
        for (int i = 1, j = 0; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long tmpTime = System.currentTimeMillis();
                long cost = tmpTime - currentTime;
                costs[j++] = cost;
                currentTime = tmpTime;
            }

            // method.invoke(null, 128); // 325ms, 4.9x

            // 3. -Dsun.reflect.noInflation=true
            // 4. -Djava.lang.Integer.IntegerCache.high=128
            // 5. -XX:TypeProfileWidth=8, default value is 2
            method.invoke(null, 128); // 133 ms, 2.0x
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

    public static void polluteProfile() throws Exception {
        Method method1 = InflationPractice.class.getMethod("target", int.class);
        Method method2 = InflationPractice.class.getMethod("target", int.class);
        for (int i = 0; i < 2000; i++) {
            method1.invoke(null, 0);
            method2.invoke(null, 0);
        }
        System.out.println("method1==method2: " + (method1 == method2)); // false
        System.out.println("method1.equals(method2): " + (method1.equals(method2))); // true
    }

    public static void target1(int i) {
    }

    public static void target2(int i) {
    }

    public static void main(String[] args) throws Exception {
        testTarget();
    }
}
