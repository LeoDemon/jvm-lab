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
import java.util.stream.Stream;

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
        Page.Url url1 = new Page.Url("/index");
        Page.Url url2 = new Page.Url("/hi");
        ReferenceQueue<Page.Url> refQueue = new ReferenceQueue<>();
        List<Page> pages = mockPages(refQueue, url1, url2);
        checkAndCollectSnapshot(pages, refQueue);

        url1 = null;
        TimeUnit.SECONDS.sleep(1);
        System.gc();

        url2 = null;
        TimeUnit.SECONDS.sleep(1);
        System.gc();

        TimeUnit.SECONDS.sleep(5);
    }

    private List<Page> mockPages(ReferenceQueue<Page.Url> refQueue, Page.Url... urls) {
        return Stream.of(urls).map(u -> mockPage(refQueue, u)).collect(Collectors.toList());
    }

    private Page mockPage(ReferenceQueue<Page.Url> urlRefQueue, Page.Url url) {
        WeakReference<Page.Url> urlRef = new WeakReference<>(url, urlRefQueue);
        Page.Snapshot snapshot = new Page.Snapshot("Test" + url.hashCode(), UUID.randomUUID().toString());
        return new Page(url.getPath(), urlRef, snapshot);
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
