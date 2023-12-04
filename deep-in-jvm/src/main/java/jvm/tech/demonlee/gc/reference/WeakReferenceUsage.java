package jvm.tech.demonlee.gc.reference;

import jvm.tech.demonlee.common.model.Student;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-11-12 15:34
 */
@Log4j2
public class WeakReferenceUsage {

    @SneakyThrows
    public void checkExists(WeakReference<Student> weakRef) {
        Student stu = weakRef.get();
        log.info("stu is enqueued to ref queue: {}, {}", weakRef.isEnqueued(), stu);
        if (Objects.isNull(stu)) {
            log.info("stu is killed.");
            log.info("stu is enqueued to ref queue: {}", weakRef.isEnqueued());
            return;
        }
        log.info("stu is alive: " + stu);
        log.info("stu is enqueued to ref queue: {}", weakRef.isEnqueued());
    }
}
