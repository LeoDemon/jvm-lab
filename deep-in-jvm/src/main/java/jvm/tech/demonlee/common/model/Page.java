package jvm.tech.demonlee.common.model;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.lang.ref.WeakReference;

/**
 * @author Demon.Lee
 * @date 2023-12-07 16:04
 */
@Log4j2
@Getter
public class Page {

    private String id;
    private WeakReference<Url> url;
    private Snapshot snapshot;

    public Page(String id, WeakReference<Url> url, Snapshot snapshot) {
        this.id = id + "-" + id.hashCode();
        this.url = url;
        this.snapshot = snapshot;
    }

    public void clearSnapshot() {
        log.info("clear snapshot now: {}", this);
        this.snapshot = null;
    }

    public static class Url {
        private final String path;

        public Url(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return "Url{" +
                    "path='" + path + '\'' +
                    '}';
        }
    }

    public static class Snapshot {
        private final String name;
        private final String value;

        public Snapshot(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Snapshot{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ",url=" + url.get() +
                ", snapshot=" + snapshot +
                '}';
    }
}