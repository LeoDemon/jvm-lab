package jvm.tech.demonlee.tmp;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Demon.Lee
 * @date 2024-08-18 08:26
 */
class Plant {
    enum LifeCycle {
        ANNUAL, PERENNIAL, BIENNIAL
    }

    final String name;
    final LifeCycle lifeCycle;

    Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        List<Plant> garden = List.of(
                new Plant("Aloe", Plant.LifeCycle.ANNUAL),
                new Plant("China rose", Plant.LifeCycle.PERENNIAL),
                new Plant("Daffodil", Plant.LifeCycle.PERENNIAL),
                new Plant("Tomato", Plant.LifeCycle.BIENNIAL));

        // 使用 ordinal() 方法将枚举类型映射到数组 - 不推荐的做法！
        Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            plantsByLifeCycle[i] = new HashSet<>();
        }
        for (Plant p : garden) {
            plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
        }
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
        }

        // 将上面的代码使用 EnumMap 重新实现
        EnumMap<LifeCycle, Set<Plant>> plantsByLifeCycle2 = new EnumMap<>(Plant.LifeCycle.class);
        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            plantsByLifeCycle2.put(lc, new HashSet<>());
        }
        for (Plant p : garden) {
            plantsByLifeCycle2.get(p.lifeCycle).add(p);
        }
        System.out.println(plantsByLifeCycle2);

        // 将上面 EnumMap 的实现方式使用 Stream 流进行简化
        EnumMap<LifeCycle, Set<Plant>> plantsByLifeCycle3 = garden.stream()
                .collect(Collectors.groupingBy(p -> p.lifeCycle,
                        () -> new EnumMap<>(Plant.LifeCycle.class),
                        Collectors.toSet()));
        System.out.println(plantsByLifeCycle3);
    }
}