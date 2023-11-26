package jvm.tech.demonlee.common.model;

import lombok.extern.log4j.Log4j2;

/**
 * @author Demon.Lee
 * @date 2023-11-12 15:26
 */
@Log4j2
public class StudentEx extends Student {

    public StudentEx(String id, String name) {
        super(id, name);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        log.info("I am finalized: " + getName());
    }
}
