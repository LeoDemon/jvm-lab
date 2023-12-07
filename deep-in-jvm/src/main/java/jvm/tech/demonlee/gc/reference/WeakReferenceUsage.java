package jvm.tech.demonlee.gc.reference;

import jvm.tech.demonlee.common.model.Page;
import jvm.tech.demonlee.common.model.Student;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @SneakyThrows
    public void usage() {
        ReferenceQueue<Page.Url> refQueue = new ReferenceQueue<>();
        List<Page> pages = mockPages(refQueue);
        checkAndCollectSnapshot(pages, refQueue);

        TimeUnit.SECONDS.sleep(1);
        System.gc();
        TimeUnit.SECONDS.sleep(10);
    }

    private List<Page> mockPages(ReferenceQueue<Page.Url> refQueue) {
        List<String> urls = List.of("hi", "index");
        return urls.stream().map(u -> mockPage(refQueue, u)).collect(Collectors.toList());
    }

    private Page mockPage(ReferenceQueue<Page.Url> urlRefQueue, String u) {
        WeakReference<Page.Url> url = new WeakReference<>(new Page.Url("/" + u), urlRefQueue);
        Page.Snapshot snapshot = new Page.Snapshot(u.substring(1), UUID.randomUUID().toString());
        return new Page(u, url, snapshot);
    }

    private void checkAndCollectSnapshot(List<Page> pages, ReferenceQueue<Page.Url> urlRefQueue) {
        Map<Reference<Page.Url>, Page> urlPageMapping = pages.stream().collect(Collectors.toMap(Page::getUrl, p -> p));
        Runnable collectionTask = () -> {
            while (true) {
                log.info("begin collect snapshot...");
                try {
                    // Reference<? extends Page.Url> url = urlRefQueue.remove(1000);
                    Reference<? extends Page.Url> url = urlRefQueue.remove();
                    if (urlPageMapping.containsKey(url)) {
                        Page page = urlPageMapping.get(url);
                        page.clearSnapshot();
                    }
                } catch (InterruptedException e) {
                    log.error("get url from queue failed: ", e);
                }
            }
        };
        new Thread(collectionTask).start();
    }
}
