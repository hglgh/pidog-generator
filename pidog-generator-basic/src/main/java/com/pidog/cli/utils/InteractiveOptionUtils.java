package com.pidog.cli.utils;

import picocli.CommandLine.Option;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: InteractiveOptionUtils
 * @Package: com.pidog.cli
 * @Description: 命令行交互式选项工具类 (可扩展王中王版)
 * @Author HGL
 * @Create: 2025/11/7 9:21
 */
public class InteractiveOptionUtils {

    /**
     * 智能确保交互选项 (V3.0)
     * 1. 【通用】预处理所有布尔类型的开关，自动补 true
     * 2. 再判断是否需要触发全交互
     *
     * @param args  命令行参数
     * @param clazz 包含交互式选项的类
     * @return 处理后的参数列表
     */
    public static List<String> ensureInteractiveOptions(String[] args, Class<?> clazz) {
        // 1. 【升级步骤】预处理所有布尔类型的选项，不再硬编码
        List<String> preprocessedArgs = preprocessBooleanOptions(args, clazz);

        // 2. 【原有步骤】根据预处理后的参数，判断是否需要触发全交互
        List<String> argList = new ArrayList<>(preprocessedArgs);
        List<String> allInteractiveOptionNames = getAllInteractiveOptionNames(clazz);

        boolean hasProvidedAnyOption = allInteractiveOptionNames.stream()
                .anyMatch(argList::contains);

        if (!hasProvidedAnyOption) {
            List<String> shortInteractiveOptions = getShortInteractiveOptionNames(clazz);
            argList.addAll(shortInteractiveOptions);
        }

        return argList;
    }

    /**
     * 【核心升级】通用的布尔开关预处理方法
     * 自动为所有无值的布尔选项补充 "true"
     *
     * @param args  原始参数数组
     * @param clazz 命令类
     * @return 预处理后的参数列表
     */
    private static List<String> preprocessBooleanOptions(String[] args, Class<?> clazz) {
        // 【新增】通过反射动态获取所有布尔选项的名称
        List<String> booleanOptionNames = getBooleanOptionNames(clazz);

        List<String> result = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            result.add(arg);

            // 【改动点】不再判断 "-l"，而是判断当前参数是否在布尔选项列表中
            if (booleanOptionNames.contains(arg)) {
                boolean isLastArg = (i == args.length - 1);
                boolean nextArgIsAnotherOption = !isLastArg && args[i + 1].startsWith("-");

                if (isLastArg || nextArgIsAnotherOption) {
                    // 如果是，说明这个布尔选项没带值，给它补上 "true"
                    result.add("true");
                }
            }
        }
        return result;
    }

    /**
     * 【新增方法】通过反射获取类中所有布尔类型选项的名称
     */
    private static List<String> getBooleanOptionNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Option.class))
                .filter(field -> field.getType() == boolean.class || field.getType() == Boolean.class)
                .map(field -> field.getAnnotation(Option.class))
                .flatMap(option -> Arrays.stream(option.names()))
                .collect(Collectors.toList());
    }

    // 以下方法保持不变...
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
