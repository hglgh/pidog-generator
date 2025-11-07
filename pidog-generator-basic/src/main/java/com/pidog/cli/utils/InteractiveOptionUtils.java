package com.pidog.cli.utils;

import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: InteractiveOptionUtils
 * @Package: com.pidog.cli
 * @Description: 命令行交互式选项工具类 (自己动手，丰衣足食版)
 * @Author HGL
 * @Create: 2025/11/7 9:21
 */
public class InteractiveOptionUtils {

    /**
     * 智能确保交互选项 (V2.0)
     * 1. 先预处理布尔开关，把 `-l` 变成 `-l true`
     * 2. 再判断是否需要触发全交互
     *
     * @param args  命令行参数
     * @param clazz 包含交互式选项的类
     * @return 处理后的参数列表
     */
    public static List<String> ensureInteractiveOptions(String[] args, Class<?> clazz) {
        // 1. 【新增步骤】预处理布尔开关选项
        List<String> preprocessedArgs = preprocessBooleanOptions(args);

        // 2. 【原有步骤】根据预处理后的参数，判断是否需要触发全交互
        List<String> argList = new ArrayList<>(preprocessedArgs);
        // 获取所有交互式选项的【所有】名称（包括长短名称）
        List<String> allInteractiveOptionNames = getAllInteractiveOptionNames(clazz);

        // 检查用户的输入参数中，是否包含了任何一个交互式选项的【任何】一个名称
        boolean hasProvidedAnyOption = allInteractiveOptionNames.stream()
                .anyMatch(argList::contains);

        // 只有当用户一个交互选项都没提供时，才强制添加所有短选项名称以触发交互
        if (!hasProvidedAnyOption) {
            List<String> shortInteractiveOptions = getShortInteractiveOptionNames(clazz);
            argList.addAll(shortInteractiveOptions);
        }

        return argList;
    }

    /**
     * 【新增方法】预处理布尔开关
     * 将 `-l` 自动转换为 `-l true`
     */
    private static List<String> preprocessBooleanOptions(String[] args) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            result.add(arg);

            // 如果当前参数是 -l 或 --loop
            if ("-l".equals(arg) || "--loop".equals(arg)) {
                // 检查它是否是最后一个参数，或者下一个参数是另一个选项（以 '-' 开头）
                boolean isLastArg = (i == args.length - 1);
                boolean nextArgIsAnotherOption = !isLastArg && args[i + 1].startsWith("-");

                if (isLastArg || nextArgIsAnotherOption) {
                    // 如果是这两种情况之一，说明 -l 没有带值，我们给它补上 "true"
                    result.add("true");
                }
            }
        }
        return result;
    }

    // 以下方法保持不变
    private static List<String> getShortInteractiveOptionNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Option.class))
                .map(field -> field.getAnnotation(Option.class))
                .filter(Option::interactive)
                .map(option -> option.names()[0])
                .collect(Collectors.toList());
    }

    private static List<String> getAllInteractiveOptionNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Option.class))
                .map(field -> field.getAnnotation(Option.class))
                .filter(Option::interactive)
                .flatMap(option -> Arrays.stream(option.names()))
                .collect(Collectors.toList());
    }
}
