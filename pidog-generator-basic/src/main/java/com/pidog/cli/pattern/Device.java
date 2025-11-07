package com.pidog.cli.pattern;

/**
 * @ClassName: Device
 * @Package: com.pidog.cli.pattern
 * @Description: 接受者(相当于被遥控的设备) —— 接收者是最终执行命令的对象，知道如何执行具体的操作。
 * @Author HGL
 * @Create: 2025/11/6 17:06
 */
public record Device(String name) {

    public void turnOn() {
        System.out.println(name + " 设备打开");
    }

    public void turnOff() {
        System.out.println(name + " 设备关闭");
    }
}

