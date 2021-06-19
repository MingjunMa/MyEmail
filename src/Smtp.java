import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Smtp {
    private String sender = null;
    private String pwd = null;
    private String receiver = null;
    private String title = null;
    private String content = null;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getPwd() {
        return pwd;
    }

    public String getSMTPServerName(){
        if (!this.sender.equals("")){
            String[] str_arr = this.sender.split("@");
            String result = str_arr[str_arr.length-1];
            return result;
        }
        else {
            return null;
        }
    }

    public void sendMail() throws Exception {
        Properties properties = new Properties();
        //设置服务器属性
        properties.setProperty("mail.host", "smtp." + getSMTPServerName());
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");
        //SSL安全连接
        if (getSMTPServerName().equals("qq.com")) {
            MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable","true");
            properties.put("mail.smtp.ssl.socketFactory",mailSSLSocketFactory);
        }

        //设置session
        Session session=Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getSender(), getPwd());
            }
        });
        session.setDebug(true);

        //通过session得到transport对象
        Transport transport=session.getTransport();
        try {
            //使用邮箱的用户名和授权码连上邮件服务器
            transport.connect("smtp." + getSMTPServerName(), this.sender, this.pwd);
            //创建邮件：写文件
            MimeMessage message=new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(this.sender));
            //指明邮件的收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(this.receiver));
            //邮件标题
            message.setSubject(this.title);
            //邮件的文本内容
            message.setContent(this.content,"text/html;charset=UTF-8");
            //5.发送邮件
            transport.sendMessage(message,message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            //6.关闭连接
            transport.close();
        }
    }
}