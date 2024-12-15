package jvm.tech.demonlee.tmp;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Demon.Lee
 * @date 2024-08-25 15:46
 */
public class FunctionTests {

    private int score;

    private void process(List<String> words) {
        // 其他业务逻辑

        words.forEach(word -> calculateScore(word));

        // 其他业务逻辑
    }

    private void calculateScore(String word) {
        System.out.println(word);
        if (word.startsWith("hello")) {
            score++;
        }
    }

    private static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .parallel()
                .limit(n)
                .reduce(0L, Long::sum);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long sum = sequentialSum(1000);
        long end = System.currentTimeMillis();
        System.out.println("sum: " + sum + ", cost: " + (end - start));
    }
}
