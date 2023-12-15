package jvm.tech.demonlee.common.model;

import java.time.LocalDate;

/**
 * @author Demon.Lee
 * @date 2023-11-12 15:26
 */
public class Student {

    private String id;

    private String name;

    private LocalDate birthDate;

    public Student() {
    }

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
