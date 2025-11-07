package com.pidog.cli;

import cn.hutool.core.util.ArrayUtil;
import com.pidog.cli.command.GenerateCommand;
import com.pidog.cli.utils.InteractiveOptionUtils;

import java.util.*;

/**
 * @ClassName: Main
 * @Package: com.pidog.cli
 * @Description: 程序入口类，支持通过反射机制动态处理不同命令
 * @Author HGL
 * @Create: 2025/11/7 10:21
 */
public class Main {
    // 命令映射表，将命令名称映射到对应的命令类
    private static final Map<String, Class<?>> COMMAND_MAP = new HashMap<>();

    static {
        // 注册支持的命令及其对应的处理类
        COMMAND_MAP.put("generate", GenerateCommand.class);
        // 可以在此添加更多命令，例如：
        // COMMAND_MAP.put("config", ConfigCommand.class);
    }

    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);
        if (ArrayUtil.isNotEmpty(args)) {
            // 查找匹配的命令并处理
            Optional<Map.Entry<String, Class<?>>> matchedCommand = COMMAND_MAP.entrySet()
                    .stream()
                    .filter(entry -> Arrays.asList(args).contains(entry.getKey()))
                    .findFirst();

            if (matchedCommand.isPresent()) {
                argsList = InteractiveOptionUtils.ensureInteractiveOptions(
                        args, matchedCommand.get().getValue());
            }
        }

        new CommandExecutor().doExecute(argsList.toArray(new String[0]));
    }
}
