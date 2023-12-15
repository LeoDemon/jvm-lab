package jvm.tech.demonlee.common.util;

/**
 * @author Demon.Lee
 * @date 2023-12-15 13:59
 */

import lombok.extern.log4j.Log4j2;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

@Log4j2
public class CompilerUtil {

    public static boolean compile(String javaFilePath, String outputDir) {
        log.info("compile file: {}, {}", javaFilePath, outputDir);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        String[] options = new String[]{
                javaFilePath,
                "-d",
                outputDir
        };
        return compiler.run(null, null, null, options) == 0;
    }
}
