package jvm.tech.demonlee.common.util;

/**
 * @author Demon.Lee
 * @date 2023-12-15 14:16
 */

import java.security.MessageDigest;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MD5Util {

    public static String getFileMD5(String filePath) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(Paths.get(filePath)));
        byte[] digest = md.digest();
        return bytesToHex(digest);
    }

    private static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
