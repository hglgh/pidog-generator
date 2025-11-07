package com.pidog.cli.pattern;

/**
 * @ClassName: TurnOnCommand
 * @Package: com.pidog.cli.pattern
 * @Description:
 * @Author HGL
 * @Create: 2025/11/6 17:04
 */
public record TurnOnCommand(Device device) implements Command {
    @Override
    public void execute() {
        device.turnOn();
    }
}
