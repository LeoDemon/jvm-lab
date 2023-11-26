package jvm.tech.demonlee.gc.reference;

import jvm.tech.demonlee.common.model.Student;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-11-12 15:34
 */
public class WeakReferenceUsage {

    public static void main(String[] args) {
        WeakReference<Student> weakRef = new WeakReference<>(new Student("10001", "LiLei"));
        checkExists(weakRef);

        System.gc();

        checkExists(weakRef);
    }

    private static void checkExists(WeakReference<Student> weakRef) {
        Student cacheStu = weakRef.get();
        if (Objects.isNull(cacheStu)) {
            System.out.println("stu is killed.");
            return;
        }
        System.out.println("stu is alive: " + cacheStu);
    }
}
