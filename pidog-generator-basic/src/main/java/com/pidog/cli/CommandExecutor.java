package com.pidog.cli;

import com.pidog.cli.command.ConfigCommand;
import com.pidog.cli.command.GenerateCommand;
import com.pidog.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * @ClassName: CommandExecutor
 * @Package: com.pidog.cli
 * @Description:
 * @Author HGL
 * @Create: 2025/11/7 10:00
 */
@Command(name = "pidog", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {
    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new ListCommand());
    }

    @Override
    public void run() {
        // 不输入子命令时，给出友好提示
        System.out.println("请输入具体命令，或者输入 --help 查看命令提示");
    }

    /**
     * 执行命令
     *
     * @param args 命令行参数
     */
    public void doExecute(String[] args) {
        commandLine.execute(args);
    }
}
