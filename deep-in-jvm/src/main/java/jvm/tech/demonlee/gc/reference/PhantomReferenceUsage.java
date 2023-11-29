package jvm.tech.demonlee.gc.reference;

import jvm.tech.demonlee.common.model.Student;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-11-12 17:49
 */
@Log4j2
public class PhantomReferenceUsage {

    public void refersTo(Student stu) {
        ReferenceQueue<Student> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Student> studentRef = new PhantomReference<>(stu, referenceQueue);
        Student stu2 = new Student("10002", "HanMeiMei");
        log.info("stu is phantom reference: {}", studentRef.refersTo(stu));
        log.info("stu2 is phantom reference: {}", studentRef.refersTo(stu2));
    }

    @SneakyThrows
    public void usageViaNotify(Student stu) {
        ReferenceQueue<Student> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Student> studentRef = new PhantomReference<>(stu, referenceQueue);
        log.info("student[{}] is: {}", stu.getName(), studentRef.get());

        stu = null;
        System.gc();

        checkReferenceExist(referenceQueue, studentRef);
    }

    @SneakyThrows
    private void checkReferenceExist(ReferenceQueue<Student> referenceQueue, PhantomReference<Student> studentRef) {
        Reference<? extends Student> cacheRef = referenceQueue.remove(3000L);
        if (Objects.isNull(cacheRef)) {
            log.info("no ref in Reference Queue...");
            return;
        }

        if (cacheRef == studentRef) {
            log.info("student was killed by GC, you can clean up some resource now...");
        }
        log.info("the phantom reference of student is: {}", cacheRef.get());
    }

    public void usageViaNotifyAndGCAgain(Student stu) {
        ReferenceQueue<Student> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Student> studentRef = new PhantomReference<>(stu, referenceQueue);
        log.info("the phantom reference of student[{}] is: {}", stu.getName(), studentRef.get());

        log.info("the first gc ...");
        stu = null;
        System.gc();

        checkReferenceExist(referenceQueue, studentRef);

        // GC again
        log.info("the second gc ...");
        System.gc();

        checkReferenceExist(referenceQueue, studentRef);
    }

    // -Xlog:gc -Xlog:gc*
    @SneakyThrows
    public void checkGC() {
        byte[] _200MB = new byte[1024 * 1024 * 200];
        ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<>();
        PhantomReference<byte[]> objRef = new PhantomReference<>(_200MB, referenceQueue);
        log.info("object is: {}", objRef.get());

        _200MB = null;
        System.gc();

        Reference<?> cacheRef = referenceQueue.remove(3000L);
        if (Objects.isNull(cacheRef)) {
            log.info("no ref in Reference Queue...");
            return;
        }

        if (cacheRef == objRef) {
            log.info("object was killed by GC, you can clean up some resource now...");
        }
        log.info("student is: {}", cacheRef.get());
    }
}
