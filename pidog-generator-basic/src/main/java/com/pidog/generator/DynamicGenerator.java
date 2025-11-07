package com.pidog.generator;

import cn.hutool.core.io.resource.ResourceUtil;
import com.pidog.model.config.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @ClassName: DynamicGenerator
 * @Package: com.pidog.generator
 * @Description:
 * @Author HGL
 * @Create: 2025/11/6 13:06
 */
public class DynamicGenerator {
    public static void main(String[] args) throws IOException {
        String inputPath = ResourceUtil.getResource("templates").getPath();
        String outputPath = String.format("%s/pidog-generator-basic/temp", System.getProperty("user.dir"));
        // 创建数据模型
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setAuthor("pidog");
        mainTemplateConfig.setOutputText("hahaha");
        generate(inputPath, outputPath,mainTemplateConfig);
    }
    public static void generate(String inputPath, String outputPath,Object dataModel) throws IOException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);
        // 指定模板文件所在的路径
        configuration.setDirectoryForTemplateLoading(new File(inputPath));
        configuration.setNumberFormat("0.######");

        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 创建模板对象，加载指定模板
        Template template = configuration.getTemplate("MainTemplate.java.ftl");
        String mainTemplate = String.format("%s/%s.java", outputPath, "MainTemplate");
        try (Writer out = new FileWriter(mainTemplate)) {
            template.process(dataModel, out);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
