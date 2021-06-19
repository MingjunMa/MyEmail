import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class Gui {

    public int[] getScreenSize(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
        int[] result = {sw, sh};
        return result;
    }

    public void initUI(){
        JFrame jf = new JFrame("Email");
        int WIDTH = 670;
        int HEIGHT = 380;
        //界面居中
        int[] screenSize = getScreenSize();
        jf.setBounds((screenSize[0] - WIDTH) / 2, (screenSize[1] - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(true);
        //frame定义
        jf.setLayout(new FlowLayout(FlowLayout.LEADING,20,20));
        //界面元素
        JButton sendButton = new JButton("发送邮件");
        sendButton.setPreferredSize(new Dimension(300,300));
        sendButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                senderUI();
            }
        });
        JButton receiveButton = new JButton("接收邮件");
        receiveButton.setPreferredSize(new Dimension(300,300));
        receiveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                receiverUI();
            }
        });
        //界面布局
        jf.add(sendButton);
        jf.add(receiveButton);
        //界面配置
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    public void senderUI(){
        JFrame senderJF = new JFrame("Sender");
        int WIDTH = 800;
        int HEIGHT = 400;
        //界面居中
        int[] screenSize = getScreenSize();
        senderJF.setBounds((screenSize[0] - WIDTH) / 2, (screenSize[1] - HEIGHT) / 2, WIDTH, HEIGHT);
        senderJF.setResizable(true);
        //界面元素
        JTextField senderJTextField = new JTextField("请输入发送者邮箱...",20);
        JTextField pwdJTextField = new JPasswordField("请输入密钥...",20);
        JTextField receiverJTextField = new JTextField("请输入接收者邮箱...",20);
        JTextField titleJTextField = new JTextField("请输入标题...",20);
        JTextArea contentJTextField = new JTextArea("请输入正文...",20,20);
        JButton sendButton = new JButton("发送");
        sendButton.setPreferredSize(new Dimension(100,100));
        //发送邮件
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Smtp newMail = new Smtp();
                newMail.setSender(senderJTextField.getText());
                newMail.setPwd(pwdJTextField.getText());
                newMail.setReceiver((receiverJTextField.getText()));
                newMail.setTitle(titleJTextField.getText());
                newMail.setContent(contentJTextField.getText());
                try {
                    newMail.sendMail();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //界面布局
        JPanel jPanel = new JPanel();
        JPanel card = new JPanel(new CardLayout());
        jPanel.add(senderJTextField);
        jPanel.add(pwdJTextField);
        jPanel.add(receiverJTextField);
        jPanel.add(titleJTextField);
        jPanel.add(contentJTextField);
        jPanel.add(sendButton);
        card.add(jPanel);
        //界面配置
        senderJF.add(card);
        senderJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        senderJF.setVisible(true);
    }

    public void receiverUI(){
        JFrame receiverJF = new JFrame("Receiver");
        int WIDTH = 800;
        int HEIGHT = 400;
        //界面居中
        int[] screenSize = getScreenSize();
        receiverJF.setBounds((screenSize[0] - WIDTH) / 2, (screenSize[1] - HEIGHT) / 2, WIDTH, HEIGHT);
        receiverJF.setResizable(true);
        //界面元素
        JTextField userJTextField = new JTextField("请输入邮箱...",20);
        JTextField pwdJTextField = new JPasswordField("请输入密钥...",20);
        JTextArea contentJTextField = new JTextArea("邮件内容...",20,20);
        JButton receiveButton = new JButton("获取");
        receiveButton.setPreferredSize(new Dimension(100,100));
        //发送邮件
        receiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pop3 receiver = new Pop3();
                receiver.setUsername(userJTextField.getText());
                receiver.setPwd(pwdJTextField.getText());
                String[] result = new String[2];
                result = receiver.receiveMail();
                contentJTextField.setText(result[0]+result[1]);
            }
        });
        //界面布局
        JPanel jPanel = new JPanel();
        JPanel card = new JPanel(new CardLayout());
        jPanel.add(userJTextField);
        jPanel.add(pwdJTextField);
        jPanel.add(contentJTextField);
        jPanel.add(receiveButton);
        card.add(jPanel);
        //界面配置
        receiverJF.add(card);
        receiverJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        receiverJF.setVisible(true);
    }
}