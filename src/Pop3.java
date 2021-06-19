import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;

import java.io.BufferedReader;
import java.io.Reader;

public class Pop3 {
    private String username = null;
    private String pwd = null;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPOP3ServerName(){
        if (!this.username.equals("")){
            String[] str_arr = this.username.split("@");
            String result = str_arr[str_arr.length-1];
            return result;
        }
        else {
            return null;
        }
    }

    public String[] receiveMail(){
        String[] result = new String[2];
        POP3Client pop3 = new POP3Client();
        try {
            pop3.setDefaultPort(110);
            pop3.connect("pop." + getPOP3ServerName());
            if (pop3.login(this.username, this.pwd)) {
                POP3MessageInfo[] p3m = pop3.listMessages();
                result[0] = "您一共有" + p3m.length + "封邮件";
                POP3MessageInfo obj = p3m[0];
                int id = obj.number;// 获得信件在服务器端的唯一编码
                Reader red = pop3.retrieveMessage(id);
                BufferedReader br = new BufferedReader(red);
                while (br.readLine() != null) {
                    result[1] = br.readLine(    );
                }
            }
            pop3.logout();
            pop3.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}