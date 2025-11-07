package com.pidog.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.pidog.model.config.MainTemplateConfig;
import lombok.Data;
import picocli.CommandLine.Command;

import java.lang.reflect.Field;

/**
 * @ClassName: ConfigCommand
 * @Package: com.pidog.cli.command
 * @Description:
 * @Author HGL
 * @Create: 2025/11/7 10:02
 */
@Data
@Command(name = "config", description = "查看参数信息", mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable {

    @Override
    public void run() {
        Field[] fields = ReflectUtil.getFields(MainTemplateConfig.class);
        for (Field field : fields) {
            System.out.println("字段名称：" + field.getName());
            System.out.println("字段类型：" + field.getType());
            System.out.println("---");
        }
    }
}
