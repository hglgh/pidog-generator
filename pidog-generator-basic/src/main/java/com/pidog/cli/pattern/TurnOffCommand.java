package com.pidog.cli.pattern;

/**
 * @ClassName: TurnOffCommand
 * @Package: com.pidog.cli.pattern
 * @Description: 具体命令(相当于遥控器的某个操作按钮)
 * 关闭设备命令 —— 具体命令是命令接口的具体实现类，它负责将请求传‌递给接收者（设备）并执行具体的操作。
 * @Author HGL
 * @Create: 2025/11/6 17:01
 */
public record TurnOffCommand(Device device) implements Command {

    @Override
    public void execute() {
        device.turnOff();
    }
}
