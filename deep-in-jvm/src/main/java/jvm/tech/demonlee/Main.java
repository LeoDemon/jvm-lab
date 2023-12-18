package jvm.tech.demonlee;

import jvm.tech.demonlee.common.model.Student;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Log4j2
public class Main {
    public static void main(String[] args) {
        log.info("Hello world!");
        checkForAgent();
    }

    @SneakyThrows
    private static void checkForAgent() {
        Student student = new Student("10001", "Jack.Ma");
        log.info("stu init: {}", student);
        while (true) {
            String agentUpdate = System.getProperty("agentUpdate");
            if (!Objects.equals(agentUpdate, "true")) {
                TimeUnit.SECONDS.sleep(3);
                continue;
            }
            System.setProperty("agentUpdate", "false");

            log.info("-------stu update: {}", student);
            log.info("-------new stu via class updates: {}", new Student("10002", "Han"));
        }
    }
}