/*
 * Copyright (c) 2015 Sohu TV. All rights reserved.
 */
package monitor.util;

/**
 * <P>
 * Description:推送邮件工具类
 * </p>
 * @author wb-jianlin
 * @version 1.0
 * @Date 2015年10月19日上午11:13:01
 */
public class PushUtils {

    /**
     * <P>
     * Description:推送方法
     * </p>
     * @author wb-jianlin
     * @version 1.0
     * @Date 2015年10月19日上午11:15:02
     * @param subject 主题
     * @param content 内容
     * @param to 收件人邮箱
     * @return
     */
    public static void push(String subject, String content, String to) {
        Message msg = new Message(Constants.MAIL_FROM_USERNAME, to, subject, content);
        new MailSender().sendMail(msg);
    }
}
