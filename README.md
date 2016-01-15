# pm2.5-monitor
send the pm information to your email

主要数据来源： pm2.5.com
目前监测天气的地址： 北京 天津（可以类似的加以修改，监测其他地区）

你需要做以下修改：

## 1.配置发送邮件的用户名和密码（仅限126邮箱）

*Constants.class*
```
public static final String MAIL_FROM_USERNAME = "";  /*发送人邮箱*/
 
public static final String MAIL_FROM_PASSWORD = "";  /*发送人密码*/
```

## 2.在*Main.class*类里面，你需要给定邮件发送到的邮箱，目前仅限126邮箱，因为服务器用的是126的邮件服务器
```
PushUtils.push("天气状况播报", content, ""); 第三个参数写要发送到的邮箱
```
## 3.运行Main方法



> 扩展使用：
在pom.xml所在目录下，输入 mvn assembly:assembly 
连同依赖的Jsoup包一同打成jar包 pm2.5-monitor-1.0-SNAPSHOT-jar-with-dependencies.jar
服务器端配置crontab 定时任务每天早上定时执行`30 6 * * * sh /opt/linjiantest/monitor-pm2.5.sh > /dev/null 2>&1`
，这样可以每天查看邮件就知道今天的空气状况了，我是这么使用的..

### monitor-pm2.5.sh
```
#!/bin/bash
/usr/local/jdk1.7.0_71/bin/java -jar /opt/linjiantest/pm2.5-monitor.jar
```
