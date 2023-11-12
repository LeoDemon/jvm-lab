package jvm.tech.demonlee.gc.reference;

import jvm.tech.demonlee.common.model.Student;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-11-12 15:34
 */
public class WeakReferenceUsage {

    public static void main(String[] args) throws InterruptedException {
        Student liLei = new Student("10001", "LiLei");
        WeakReference<Student> studentSoftRef = new WeakReference<>(liLei);
        checkExists(studentSoftRef);

        liLei = null;
        System.gc();

        // Thread.sleep(2000L);

        checkExists(studentSoftRef);
    }

    private static void checkExists(WeakReference<Student> studentSoftRef) {
        Student cacheStu = studentSoftRef.get();
        if (Objects.isNull(cacheStu)) {
            System.out.println("liLei is killed.");
            return;
        }
        System.out.println("liLei is alive: " + cacheStu);
    }
}
