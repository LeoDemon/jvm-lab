package jvm.tech.demonlee.tmp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2024-06-01 10:11
 */
public class FooBar implements Cloneable {

    private String name;
    private final int score = 99;
    private List<String> values;

    public FooBar(String name, List<String> values) {
        this.name = name;
        this.values = new ArrayList<>(values);
    }

    public FooBar(FooBar fooBar) {
        this.name = fooBar.name;
        this.values = new ArrayList<>(fooBar.values);
    }

    public static FooBar newInstance(FooBar fooBar) {
        // return new FooBar(fooBar);
        return new FooBar(fooBar.name, new ArrayList<>(fooBar.values));
    }

    public void addValue(String value) {
        values.add(value);
    }

    public void removeValue(String value) {
        values.remove(value);
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
}
