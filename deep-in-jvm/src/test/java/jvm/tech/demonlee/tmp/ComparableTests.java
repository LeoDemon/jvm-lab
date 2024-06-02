package jvm.tech.demonlee.tmp;

import jvm.tech.demonlee.tmp.model.FooBar;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Demon.Lee
 * @date 2024-06-02 11:52
 */
@Log4j2
class ComparableTests {

    @Test
    void should_storage_with_set() {
        addAndDisplay(new HashSet<>());
        addAndDisplay(new TreeSet<>());
    }

    private static void addAndDisplay(Set<BigDecimal> set) {
        set.add(new BigDecimal("3.0"));
        set.add(new BigDecimal("3.00"));
        log.info("{}: {}", set.getClass().getSimpleName(), set);
    }

    @Test
    void should_sort() {
        List<FooBar> fooBars = new ArrayList<>();
        fooBars.add(new FooBar("Jack.马", 56));
        fooBars.add(new FooBar("Andrew", 77));
        fooBars.add(new FooBar("An子奇", 56));
        fooBars.add(new FooBar("Tim", 98));
        log.info("fooBars: {}", fooBars.stream().map(FooBar::getNameAndScore).collect(Collectors.joining(", ")));
        Collections.sort(fooBars);
        log.info("sorted fooBars: {}", fooBars.stream().map(FooBar::getNameAndScore).collect(Collectors.joining(", ")));
    }
}
