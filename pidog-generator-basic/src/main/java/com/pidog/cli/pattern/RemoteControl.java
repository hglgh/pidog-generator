package com.pidog.cli.pattern;

import lombok.Setter;

/**
 * @ClassName: RemoteControl
 * @Package: com.pidog.cli.pattern
 * @Description: 调用者(相当于遥控器) —— 作用是接受客户端的命令并执行。
 * @Author HGL
 * @Create: 2025/11/6 17:12
 */
@Setter
public class RemoteControl {
    private Command command;

    public void pressButton() {
        command.execute();
    }
}

