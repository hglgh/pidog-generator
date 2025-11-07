package com.pidog.generator;

import com.pidog.model.config.MainTemplateConfig;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName: MainGenerator
 * @Package: com.pidog.generator
 * @Description: 核心生成器
 * @Author HGL
 * @Create: 2025/11/6 13:24
 */
public class MainGenerator {
    public static void main(String[] args) throws IOException {
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setAuthor("pidog");
        mainTemplateConfig.setOutputText("hahaha");
        doGenerate(mainTemplateConfig);
    }

    public static void doGenerate(Object model) throws IOException {
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();
        String rootPath = parentFile.getAbsolutePath();
        // 整个项目的根路径
        System.out.println(rootPath);
        String inputPath = String.format("%s/pidog-generator-demo-projects/acm-template", rootPath);
        String outputPath = String.format("%s/pidog-generator-basic/temp", rootPath);
        // 生成静态文件
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);
        String inputDynamicFilePath = String.format("%s/pidog-generator-basic/src/main/resources/templates", rootPath);
        String outputDynamicFilePath = String.format("%s/pidog-generator-basic/temp/acm-template/src/com/pidog/acm", rootPath);
        // 生成动态文件
        DynamicGenerator.generate(inputDynamicFilePath, outputDynamicFilePath, model);

    }
}
