package com.pidog.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @ClassName: StaticGenerator
 * @Package: com.pidog.generator
 * @Description: 静态文件生成器
 * @Author HGL
 * @Create: 2025/11/5 14:46
 */
public class StaticGenerator {
    public static void main(String[] args) {
        // 获取项目路径
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);

        File dir = new File("pidog-generator-demo-projects/acm-template");
        System.out.println(dir.getAbsoluteFile());
        String outPath = String.format("%s/pidog-generator-basic/temp", projectPath);
//        copyFilesByHutool(dir.getAbsolutePath(), outPath);
        copyFilesByRecursive(dir.getAbsolutePath(), outPath);
    }

    /**
     * 拷贝文件（Hutool 实现，会将输入目录完整拷贝到输出目录下）
     *
     * @param inputPath  输入目录
     * @param outputPath 输出目录
     */
    public static void copyFilesByHutool(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);
    }

    /**
     * 递归拷贝文件（递归实现，会将输入目录完整拷贝到输出目录下）
     *
     * @param inputPath  输入目录
     * @param outputPath 输出目录
     */
    public static void copyFilesByRecursive(String inputPath, String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try {
            copyFileByRecursive(inputFile, outputFile);
        } catch (Exception e) {
            System.err.println("文件复制失败:" + e.getMessage());
        }
    }

    /**
     * 文件 A => 目录 B，则文件 A 放在目录 B 下
     * 文件 A => 文件 B，则文件 A 覆盖文件 B
     * 目录 A => 目录 B，则目录 A 放在目录 B 下
     * <p>
     * 核心思路：先创建目录，然后遍历目录内的文件，依次复制
     *
     * @param inputFile  输入文件
     * @param outputFile 输出文件
     * @throws IOException IO异常
     */
    private static void copyFileByRecursive(File inputFile, File outputFile) throws IOException {
        if (inputFile.isDirectory()) {
            System.out.println(inputFile.getName());
            File destOutputFile = new File(outputFile, inputFile.getName());
            // 如果是目录，首先创建目标目录
            if (!destOutputFile.exists()) {
                boolean mkdirs = destOutputFile.mkdirs();
                if (!mkdirs) {
                    System.err.println("创建目录失败:" + destOutputFile.getAbsolutePath());
                }
            }
            File[] files = inputFile.listFiles();
            if (ArrayUtil.isEmpty(files)) {
                return;
            }
            for (File file : files) {
                copyFileByRecursive(file, destOutputFile);
            }
        } else {
            // 是文件，直接复制到目标目录下
            Path destPath = outputFile.toPath().resolve(inputFile.getName());
            Files.copy(inputFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
