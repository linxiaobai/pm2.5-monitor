package monitor.util;

/**
 * Created by jianlin210349 on 2016/1/15.
 */

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MailSender {
    private static final int port = 25;

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut, true);//true表示自动将中间缓存flush到接受数据端
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public void sendMail(Message msg) {
        Socket socket = null;

        try {
            socket = new Socket(Constants.MAIL_SERVER_HOST, port); //连接到邮件服务器
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            String localhost = InetAddress.getLocalHost().getHostName();  //客户主机的名字

            BASE64Encoder base64Encoder = new BASE64Encoder();
            String username = base64Encoder.encode(Constants.MAIL_FROM_USERNAME.getBytes());   //写你的163邮箱账户
            String password = base64Encoder.encode(Constants.MAIL_FROM_PASSWORD.getBytes());    //写你的密码

            sendAndReceive(null, br, pw);  //仅仅是为了接收服务器的响应数据
            sendAndReceive("HELO " + localhost, br, pw);
            sendAndReceive("AUTH LOGIN", br, pw); //认证命令
            sendAndReceive(username, br, pw);  //用户名
            sendAndReceive(password, br, pw);  //密码
            sendAndReceive("MAIL FROM:<" + msg.from + ">", br, pw);
            sendAndReceive("RCPT TO:<" + msg.to + ">", br, pw);
            sendAndReceive("DATA", br, pw);       //接下来开始发送邮件内容
            pw.println(msg.data);
            System.out.println("Client>" + msg.data);
            sendAndReceive(".", br, pw); //邮件发送完毕
            sendAndReceive("QUIT", br, pw);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 发送一行字符串，并且接受一行服务器的响应数据
     *
     * @throws IOException
     */
    private void sendAndReceive(String str, BufferedReader br, PrintWriter pw) throws IOException {
        if (str != null) {
            System.out.println("Client>" + str);
            pw.println(str);       //发送完str字符串后，还会发送"\r\n"
        }
        String response;
        if ((response = br.readLine()) != null) {
            System.out.println("Server>" + response);
        }
    }
}


class Message {
    protected final String from;  //发送者的邮件地址
    protected final String to;   //接收者的邮件地址
    protected final String subject;  //邮件标题
    protected final String content;  //邮件正文
    protected final String data;    //邮件内容，包括邮件标题和正文

    public Message(String from, String to, String subject, String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        data = "Subject:" + subject + "\n\r" + content;
    }
}
