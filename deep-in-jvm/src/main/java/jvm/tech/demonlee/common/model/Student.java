package jvm.tech.demonlee.common.model;

/**
 * @author Demon.Lee
 * @date 2023-11-12 15:26
 */
public class Student {

    private final String id;

    private final String name;

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
                '}';
    }

    // @Override
    // protected void finalize() throws Throwable {
    //     super.finalize();
    //     System.out.println("I am finalized: " + name);
    // }
}
