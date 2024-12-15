package jvm.tech.demonlee.tmp;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Demon.Lee
 * @date 2024-11-24 07:56
 */
@Log4j2
class GeneralLibTests {

    @Test
    void should_parse_url_content() {
        String url = "https://www.deepin.org/index/zh";
        try (InputStream is = new URL(url).openStream()) {
            // 直接输出
            // is.transferTo(System.out);

            // 输出到字符串
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            is.transferTo(byteOS);
            String content = byteOS.toString(StandardCharsets.UTF_8);
            log.info("byte array output stream: {}", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Random random = new Random();

    @Test
    void should_get_random_number() {
        int n = 2 * (Integer.MAX_VALUE / 3);
        log.info("n: {}", n);
        int low = 0;
        for (int i = 0; i < 1000000; i++) {
            // 使用 Random.nextInt() 方法，low 接近于 666666
            int r = random1(n);

            // 使用 Random.nextInt(int n)方法，则 low 接近于 500000
            // int r = random2(n);

            // 使用 ThreadLocalRandom.nextInt()方法，则 low 接近于 500000
            // int r = random3(n);

            if (r < n / 2) {
                low++;
            }
        }
        log.info("low: {}", low);
    }

    private int random1(int n) {
        int number = random.nextInt();
        return Math.abs(number) % n;
    }

    private int random2(int n) {
        int number = random.nextInt(n);
        return Math.abs(number) % n;
    }

    private int random3(int n) {
        int number = ThreadLocalRandom.current().nextInt(n);
        return Math.abs(number) % n;
    }

    @Test
    void should_join_string_via_string_builder() {
        int n = 1000000;
        StringBuilder sb = new StringBuilder();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            sb.append("a");
        }
        String result = sb.toString();
        long endTime = System.currentTimeMillis();
        System.out.println("使用 StringBuilder 连接 " + n + " 个字符串耗时: " + (endTime - startTime) + " 毫秒");
    }

    @Test
    void should_join_string_via_plus_operator() {
        String prefix = "class";
        String suffix = "object";
        String result = prefix + "#" + suffix;
        System.out.println(result);
    }

    @Test
    void should_reflective_instantiation() {
        String[] args = {"java.util.LinkedHashSet", "LinkedHashSet", "java", "Tree", "Hello", "你好"};
        reflectiveInstantiation(args);
    }

    void fatalError(String errMsg) {
        System.err.println("Fatal Error: " + errMsg);
        System.exit(1);
    }

    void reflectiveInstantiation(String[] args) {
        // 获取 Class 对象
        Class<? extends Set<String>> clazz = null;
        try {
            clazz = (Class<? extends Set<String>>) Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            fatalError("class not found: " + args[0]);
        }

        // 获取构造器
        Constructor<? extends Set<String>> cons = null;
        try {
            cons = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fatalError("constructor not found: " + clazz);
        }

        // 创建对象
        Set<String> set = null;
        try {
            set = cons.newInstance();
        } catch (InvocationTargetException e) {
            fatalError("constructor invocation failed: " + clazz);
        } catch (InstantiationException e) {
            fatalError("instantiation exception: " + clazz);
        } catch (IllegalAccessException e) {
            fatalError("constructor is not accessible: " + clazz);
        } catch (IllegalArgumentException e) {
            fatalError("invalid arguments: " + clazz);
        }

        // 添加元素
        List<String> argsList = Arrays.asList(args);
        List<String> argsList2 = argsList.subList(1, argsList.size());
        set.addAll(argsList2);
        System.out.println("args: " + set);
    }
}
