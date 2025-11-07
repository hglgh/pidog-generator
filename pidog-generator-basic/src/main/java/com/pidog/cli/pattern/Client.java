package com.pidog.cli.pattern;

/**
 * @ClassName: Client
 * @Package: com.pidog.cli.pattern
 * @Description: 客户端(相当于使用遥控器的人)
 * —— 客户端的作用是创建命令对象并将其与接收者关联（绑定‌设备），然后将命令对象传递给调用者（按遥控器），从而触发执行。
 * @Author HGL
 * @Create: 2025/11/6 17:13
 */
public class Client {
    public static void main(String[] args) {
        // 创建接收者对象
        Device tv = new Device("TV");
        Device stereo = new Device("Stereo");

        // 创建具体命令对象，可以绑定不同设备
        TurnOnCommand turnOn = new TurnOnCommand(tv);
        TurnOffCommand turnOff = new TurnOffCommand(stereo);

        // 创建调用者
        RemoteControl remote = new RemoteControl();

        // 执行命令
        remote.setCommand(turnOn);
        remote.pressButton();

        remote.setCommand(turnOff);
        remote.pressButton();
    }
}

