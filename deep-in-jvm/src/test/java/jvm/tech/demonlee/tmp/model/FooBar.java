package jvm.tech.demonlee.tmp.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2024-06-01 10:11
 */
public class FooBar implements Cloneable, Comparable<FooBar> {

    private String name;
    private int score;
    private final List<String> values;

    public FooBar(String name, List<String> values) {
        this.name = name;
        this.values = new ArrayList<>(values);
    }

    public FooBar(String name, int score) {
        this.name = name;
        this.score = score;
        this.values = new ArrayList<>();
    }

    public FooBar(String name, int score, List<String> values) {
        this.name = name;
        this.score = score;
        this.values = values;
    }

    public FooBar(FooBar fooBar) {
        this.name = fooBar.name;
        this.score = fooBar.score;
        this.values = new ArrayList<>(fooBar.values);
    }

    public static FooBar newInstance(FooBar fooBar) {
        // return new FooBar(fooBar);
        return new FooBar(fooBar.name, fooBar.score, new ArrayList<>(fooBar.values));
    }

    public void addValue(String value) {
        values.add(value);
    }

    public void removeValue(String value) {
        values.remove(value);
    }

    public String getNameAndScore() {
        return name + "-" + score;
    }

    @Override
    public String toString() {
        return "FooBar{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FooBar fooBar)) return false;
        return score == fooBar.score && Objects.equals(name, fooBar.name) &&
                Objects.equals(values, fooBar.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, values);
    }

    @Override
    public FooBar clone() {
        try {
            return (FooBar) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(FooBar o) {
        // Warning: don't use the difference between two variables like below
        // return this.score - o.score;

        // int result = Integer.compare(this.score, o.score);
        // if (result == 0) {
        //     result = this.name.compareTo(o.name);
        // }
        // return result;

        return Comparator.comparingInt((FooBar o1) -> o1.score).thenComparing((FooBar o1) -> o1.name).compare(this, o);
    }
}
