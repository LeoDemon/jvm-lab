package jvm.tech.demonlee.tmp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Demon.Lee
 * @date 2024-10-03 09:18
 */
class CollectionTests {

    @Test
    void remove_set_and_list_element() {
        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new TreeSet<>();

        for (int i = -3; i < 3; i++) {
            list.add(i);
            set.add(i);
        }

        for (int i = 0; i < 3; i++) {
            list.remove(i);
            // list.remove((Integer)i);
            set.remove(i);
        }

        System.out.println("list: " + list);
        System.out.println("set: " + set);
    }

    public static void main(String[] args) {
        // ExecutorService executorService = Executors.newCachedThreadPool();
        // executorService.submit(System.out::println);
        System.out.printf("%s", "Hello World");
        System.out.printf("%s, %d", "Hello World", 100);
    }
}
