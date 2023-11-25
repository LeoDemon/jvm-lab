package jvm.tech.demonlee.gc.reference;

import jvm.tech.demonlee.common.model.Student;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.lang.ref.SoftReference;
import java.util.*;

/**
 * @author Demon.Lee
 * @date 2023-11-12 15:24
 */
@Log4j2
public class SoftReferenceUsage {

    @SneakyThrows
    public void outOfMemoryError() {
        // 200MB
        SoftReference<byte[]> softRef = new SoftReference<>(new byte[1024 * 1024 * 200]);
        log.info("Soft Reference created: {}", getSoftReferenceHashCode(softRef));

        // 建议 JVM 触发垃圾回收
        System.gc();
        // 等待垃圾回收完成
        Thread.sleep(3000);

        log.info("After GC: {}", getSoftReferenceHashCode(softRef));

        // 创建一些对象以产生内存压力
        List<byte[]> memoryPressures = new ArrayList<>();
        try {
            for (int i = 0; ; i++) {
                // 每次分配 10MB
                memoryPressures.add(new byte[1024 * 1024 * 10]);
                log.info("{}, Soft Reference under pressure: {}", i, getSoftReferenceHashCode(softRef));
            }
        } catch (OutOfMemoryError error) {
            // 当内存不足时，软引用对象应该被回收
            log.error("After running out of memory: {}", getSoftReferenceHashCode(softRef), error);
        }
    }

    private Integer getSoftReferenceHashCode(SoftReference<byte[]> softRef) {
        return Optional.ofNullable(softRef.get()).map(Object::hashCode).orElse(null);
    }

    @SneakyThrows
    public void outOfMemoryError4HardReference() {
        // 200MB
        byte[] hardRef = new byte[1024 * 1024 * 200];
        log.info("Hard Reference created: {}", Arrays.hashCode(hardRef));

        // 建议 JVM 触发垃圾回收
        System.gc();
        // 等待垃圾回收完成
        Thread.sleep(3000);

        log.info("After GC: {}", Arrays.hashCode(hardRef));

        // 创建一些对象以产生内存压力
        List<byte[]> memoryPressures = new ArrayList<>();
        try {
            for (int i = 1; ; i++) {
                // 每次分配 10MB
                memoryPressures.add(new byte[1024 * 1024 * 10]);
                log.info("{}, Hard Reference under pressure: {}", i, Arrays.hashCode(hardRef));
            }
        } catch (OutOfMemoryError error) {
            log.error("After running out of memory: {}", Arrays.hashCode(hardRef), error);
        }
    }

    public void usage() {
        Student stu = new Student("10001", "LiLei");
        SoftReference<Student> studentSoftRef = new SoftReference<>(stu);
        Student cacheStu = studentSoftRef.get();
        if (Objects.isNull(cacheStu)) {
            // 若被回收，需要重新赋值
        }

        List<Student> students = new ArrayList<>(List.of(stu));
        SoftReference<List<Student>> studentsSoftRef = new SoftReference<>(students);
        List<Student> cacheStudents = studentsSoftRef.get();
        if (Objects.isNull(cacheStudents)) {
            // 若被回收，需要重新赋值
        }
    }
}
