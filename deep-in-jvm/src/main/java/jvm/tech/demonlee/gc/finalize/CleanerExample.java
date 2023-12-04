package jvm.tech.demonlee.gc.finalize;

import lombok.extern.log4j.Log4j2;

import java.lang.ref.Cleaner;

/**
 * @author Demon.Lee
 * @date 2023-12-01 13:52
 */
@Log4j2
public class CleanerExample implements AutoCloseable {

    // A cleaner, preferably one shared within a library
    private static final Cleaner cleaner = Cleaner.create();
    private final State state;
    private final Cleaner.Cleanable cleanable;

    public CleanerExample() {
        this.state = new State();
        this.cleanable = cleaner.register(this, state);
    }

    public CleanerExample(boolean nonStaticClass) {
        if (nonStaticClass) {
            this.state = new StateEx();
        } else {
            this.state = new State();
        }
        this.cleanable = cleaner.register(this, state);
    }

    public void execute() {
        log.info("do sth via state: {}", state);
    }

    @Override
    public void close() {
        cleanable.clean();
    }

    // static inner class
    static class State implements Runnable {

        private byte[] memory;

        State() {
            // initialize State needed for cleaning action
            log.info("create some resources here...");
            memory = new byte[1024 * 1024 * 200];
        }

        @Override
        public void run() {
            // cleanup action accessing State, executed at most once
            log.info("release some resources now...");
            memory = null;
        }
    }

    // non-static inner class
    class StateEx extends State {
    }
}
