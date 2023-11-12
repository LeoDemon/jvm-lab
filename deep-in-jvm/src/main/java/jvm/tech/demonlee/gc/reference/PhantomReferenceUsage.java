package jvm.tech.demonlee.gc.reference;

import jvm.tech.demonlee.common.model.Student;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-11-12 17:49
 */
public class PhantomReferenceUsage {
    static ReferenceQueue<Student> referenceQueue = new ReferenceQueue<>();

    public static void main(String[] args) throws InterruptedException {
        Student liLei = new Student("10001", "LiLei");
        PhantomReference<Student> studentRef = new PhantomReference<>(liLei, referenceQueue);

        liLei = null;
        System.gc();

        Reference<? extends Student> cacheStuRef = referenceQueue.remove(2000L);
        if (Objects.isNull(cacheStuRef)) {
            System.out.println("no ref in Reference Queue...");
            return;
        }
        System.out.println("student ref was polled in Reference Queue...");
        Student student = cacheStuRef.get(); // get always return null
        System.out.println("student is: " + student);
    }
}
