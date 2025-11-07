#!/usr/bin/env pwsh

# 【作弊码，内置！】每次运行脚本，自动执行
[Console]::InputEncoding = [System.Text.Encoding]::UTF8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

# 启动你的Java程序
java '-Dfile.encoding=UTF-8' -jar 'target/pidog-generator-basic-1.0-SNAPSHOT-jar-with-dependencies.jar' $args
