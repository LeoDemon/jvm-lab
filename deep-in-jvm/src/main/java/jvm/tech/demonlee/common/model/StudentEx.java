package jvm.tech.demonlee.common.model;

import lombok.extern.log4j.Log4j2;

/**
 * @author Demon.Lee
 * @date 2023-11-12 15:26
 */
@Log4j2
public class StudentEx extends Student {

    private byte[] memory;

    public StudentEx(String id, String name) {
        super(id, name);
    }

    public StudentEx(String id, String name, byte[] memory) {
        super(id, name);
        this.memory = memory;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            log.info("I am finalized: {}", getName());
            // release some resources here...
        } catch (Throwable throwable) {
            log.error("finalize failed: ", throwable);
        } finally {
            super.finalize();
        }
    }
}
