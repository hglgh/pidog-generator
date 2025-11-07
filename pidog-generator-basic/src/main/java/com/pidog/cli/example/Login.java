package com.pidog.cli.example;

import com.pidog.cli.utils.InteractiveOptionUtils;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author 请别把我整破防
 */
public class Login implements Callable<Integer> {
    @Option(names = {"-u", "--user"}, arity = "0..1", interactive = true,echo = true, description = "请输入用户名")
    String user;

    @Option(names = {"-p", "--password"}, description = "Passphrase", arity = "0..1", interactive = true, prompt = "请输入密码: ")
    String password;

    @Option(names = {"-cp", "--checkPassword"}, description = "Check Password", arity = "0..1", interactive = true, prompt = "请输入校验密码: ")
    String checkPassword;

    @Override
    public Integer call() {
        System.out.println("user = " + user);
        System.out.println("password = " + password);
        System.out.println("checkPassword = " + checkPassword);
        return 0;
    }

    public static void main(String[] args) {
        List<String> processedArgs = InteractiveOptionUtils.ensureInteractiveOptions(args, Login.class);
        new CommandLine(new Login()).execute(processedArgs.toArray(new String[0]));
    }

}