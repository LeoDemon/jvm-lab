package jvm.tech.demonlee.reflection.inflation;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Demon.Lee
 * @date 2023-08-08 21:40
 */
public class InflationV5 {

    public static void target(int i) {
    }

    public static void testTarget() throws Exception {
        Class<?> clazz = Class.forName(InflationV5.class.getName());
        Method method = clazz.getMethod("target", int.class);
        // 1.close access auth check
        method.setAccessible(true);

        // 2. more profile to interfere with method inline
        polluteProfile();

        Object[] objects = new Object[1];
        objects[0] = 128;
        long[] costs = new long[20];
        long currentTime = System.currentTimeMillis();
        for (int i = 1, j = 0; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long tmpTime = System.currentTimeMillis();
                long cost = tmpTime - currentTime;
                costs[j++] = cost;
                currentTime = tmpTime;
            }

            // 3. -Dsun.reflect.noInflation=true
            // 4. -Djava.lang.Integer.IntegerCache.high=128
            // method.invoke(null, 128); // 477 ms, 7.1x

            // 5. use object array
            // method.invoke(null, objects); // 315 ms, 4.7x

            // 6. -XX:TypeProfileWidth=8, default value is 2
            method.invoke(null, objects); // 202 ms, 3.0x
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
        Method method1 = InflationV5.class.getMethod("target1", int.class);
        Method method2 = InflationV5.class.getMethod("target2", int.class);
        for (int i = 0; i < 2000; i++) {
            method1.invoke(null, 0);
            method2.invoke(null, 0);
        }
    }

    public static void target1(int i) {
    }

    public static void target2(int i) {
    }

    public static void main(String[] args) throws Exception {
        testTarget();
    }
}
