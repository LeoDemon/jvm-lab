package jvm.tech.demonlee.memory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author Demon.Lee
 * @date 2023-12-18 20:37
 */
public class MetaspaceMemoryExample {

    public static void main(String[] args) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        System.out.println("方法区（元空间）信息：");
        System.out.println("\t方法区（元空间）初始大小: " + nonHeapMemoryUsage.getInit() / 1024 + "KB");
        System.out.println("\t方法区（元空间）最大大小: " +
                (nonHeapMemoryUsage.getMax() == -1 ? "无限制" : nonHeapMemoryUsage.getMax() / 1024 + "KB"));
        System.out.println("\t方法区（元空间）已用大小: " + nonHeapMemoryUsage.getUsed() / 1024 + "KB");

        MemoryPoolMXBean metaspaceMemoryPoolBean = getMemoryPoolMXBean();
        if (metaspaceMemoryPoolBean != null) {
            System.out.println("\nMetaspace 信息：");
            MemoryUsage metaspaceMemoryUsage = metaspaceMemoryPoolBean.getUsage();
            System.out.println("\tMetaspace 初始大小: " + metaspaceMemoryUsage.getInit() / 1024 + "KB");
            System.out.println("\tMetaspace 最大大小: " +
                    (metaspaceMemoryUsage.getMax() == -1 ? "无限制" : metaspaceMemoryUsage.getMax() / 1024 + "KB"));
            System.out.println("\tMetaspace 已用大小: " + metaspaceMemoryUsage.getUsed() / 1024 + "KB");
        }
    }

    private static MemoryPoolMXBean getMemoryPoolMXBean() {
        for (MemoryPoolMXBean memoryPoolBean : ManagementFactory.getMemoryPoolMXBeans()) {
            if ("Metaspace".equals(memoryPoolBean.getName())) {
                return memoryPoolBean;
            }
        }
        return null;
    }
}
