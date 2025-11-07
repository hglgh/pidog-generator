package com.pidog.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.pidog.generator.MainGenerator;
import com.pidog.model.config.MainTemplateConfig;
import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

/**
 * @ClassName: GenerateCommand
 * @Package: com.pidog.cli.command
 * @Description:
 * @Author HGL
 * @Create: 2025/11/7 10:02
 */
@Data
@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
public class GenerateCommand implements Callable<Integer> {
    @Option(names = {"-l", "--loop"}, description = "是否循环", arity = "0..1", echo = true, interactive = true)
    private boolean loop;

    @Option(names = {"-a", "--author"}, description = "作者", arity = "0..1", echo = true, interactive = true)
    private String author = "pidog";

    @Option(names = {"-o", "--output"}, description = "输出信息", arity = "0..1", echo = true, interactive = true)
    private String outputText = "输出信息";

    @Override
    public Integer call() throws Exception {
        MainTemplateConfig mainTemplateConfig = BeanUtil.copyProperties(this, MainTemplateConfig.class);
        System.out.println("配置信息：" + mainTemplateConfig);
        MainGenerator.doGenerate(mainTemplateConfig);
        return 0;
    }
}
