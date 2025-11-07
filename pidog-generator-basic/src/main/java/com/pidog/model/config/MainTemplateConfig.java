package com.pidog.model.config;

import lombok.Data;

/**
 * @ClassName: MainTemplateConfig
 * @Package: com.pidog.model.config
 * @Description: 动态模版配置
 * @Author HGL
 * @Create: 2025/11/6 11:28
 */
@Data
public class MainTemplateConfig {

    /**
     * 是否生成循环
     */
    private boolean loop = false;

    /**
     * 作者注释
     */
    private String author = "HGL";

    /**
     * 输出信息
     */
    private String outputText = "输出信息";
}
