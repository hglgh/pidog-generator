package com.pidog.cli.utils;

import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: InteractiveOptionUtils
 * @Package: com.pidog.cli
 * @Description: 命令行交互式选项工具类
 * 用于处理带有交互式选项的命令行参数
 * @Author HGL
 * @Create: 2025/11/7 9:21
 */
public class InteractiveOptionUtils {

    /**
     * 确保所有交互式选项都有相应的命令行参数
     *
     * @param args  命令行参数
     * @param clazz 包含交互式选项的类
     * @return 处理后的参数列表
     */
    public static List<String> ensureInteractiveOptions(String[] args, Class<?> clazz) {
        List<String> argList = new ArrayList<>(Arrays.asList(args));
        List<String> interactiveOptions = getInteractiveOptionNames(clazz);

        for (String option : interactiveOptions) {
            if (!argList.contains(option)) {
                argList.add(option);
            }
        }
        return argList;
    }

    /**
     * 获取指定类中所有交互式选项的名称
     *
     * @param clazz 包含选项定义的类
     * @return 交互式选项名称列表
     */
    public static List<String> getInteractiveOptionNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Option.class))
                .map(field -> field.getAnnotation(Option.class))
                .filter(Option::interactive)
                .map(option -> option.names()[0])
                .collect(Collectors.toList());
    }
}