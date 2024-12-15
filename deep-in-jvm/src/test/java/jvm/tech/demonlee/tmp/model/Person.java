package jvm.tech.demonlee.tmp.model;

/**
 * @author Demon.Lee
 * @date 2024-08-26 10:11
 */
public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
