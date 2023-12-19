package jvm.tech.demonlee.classloader.hotswap;

import jvm.tech.demonlee.common.util.MD5Util;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-12-15 14:40
 */
@Log4j2
public class FileDigest {

    private String currentValue;
    private final String path;

    public FileDigest(String path) throws Exception {
        this.path = path;
        this.currentValue = MD5Util.getFileMD5(path);
    }

    public boolean isNotChanged() throws Exception {
        return !isChanged();
    }

    public boolean isChanged() throws Exception {
        String newValue = MD5Util.getFileMD5(path);
        if (Objects.equals(currentValue, newValue)) {
            return false;
        }
        log.info("file has been changed...");
        this.currentValue = newValue;
        return true;
    }
}
