package jvm.tech.demonlee.tmp;

import java.math.BigInteger;
import java.util.stream.LongStream;

/**
 * @author Demon.Lee
 * @date 2024-09-17 17:04
 */
public class PrimeCounting {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long count = pi(10000000L);
        long end = System.currentTimeMillis();
        System.out.println("count: " + count + ", time: " + (end - start) + "ms");
    }

    static long pi(long n) {
        return LongStream.rangeClosed(2, n)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
    }
}