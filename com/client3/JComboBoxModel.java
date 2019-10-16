package com.client3;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.client.*;

/*聊天室类，负责保存当前登陆的每一个用户，并且当某一个用户给服务器发信息时，他需要立刻把这条信息转发给其他客户机*/
@SuppressWarnings("serial")
public class JComboBoxModel extends JFrame {
    private JScrollPane Txtchat;
    static JTextField Txtinput;
    private JTextField Txtip,Txtname;
    private JTextField Txtport;
    static JList<String> jusernamelist;

    JComboBoxModel(){
        super("魔方小镇v1.2");
        setBounds(100,100,1050,700);
        Container c = getContentPane();
        c.setLayout(null);
        clientelem clientElem = new clientelem();

        JButton jbtxt = new JButton("文字发送");jbtxt.setBounds(900,490,110,60);c.add(jbtxt);
        JButton jbpic = new JButton("文件发送");jbpic.setBounds(900,560,110,60);c.add(jbpic);

        Txtname = new JTextField(null);Txtname.setEditable(false);
        Txtip = new JTextField(null);Txtip.setEditable(false);//服务器IP
        Txtport = new JTextField(null);Txtport.setEditable(false);//端口号

        jusernamelist = clientElem.getUserNameList();
        Txtinput = clientElem.getMessage();//打字文本框
        JTextArea ta = clientElem.getBody();
        ta.setLineWrap(true);//设置自动换行
        ta.setEditable(false);
        Txtchat = new JScrollPane(ta);//聊天文本框

        /*添加文本框*/
        Txtinput.setBounds(210,490,690,130);
        Txtip.setBounds(90,20,200,40);
        Txtchat.setBounds(210,100,800,360);
        Txtport.setBounds(390,20,200,40);
        jusernamelist.setBounds(30,120,150,500);
        Txtname.setBounds(700,20,200,40);
        Txtname.setHorizontalAlignment(JTextField.CENTER);
        c.add(Txtinput);
        c.add(Txtip);
        c.add(Txtchat);
        c.add(Txtport);
        c.add(jusernamelist);
        c.add(Txtname);
        Txtchat.setFocusable(false);

        /*添加标签*/
        JLabel j1 = new JLabel("服务器IP:");
        JLabel j2 = new JLabel("端口号:");
        JLabel j3 = new JLabel("用户名：");
        JLabel j4 = new JLabel("当前在线用户:");

        j1.setBounds(30,20,60,40);
        j2.setBounds(330,20,60,40);
        j3.setBounds(610,20,100,40);
        j4.setBounds(30,80,100,40);
        c.add(j1);
        c.add(j2);
        c.add(j3);
        c.add(j4);

        c.setBackground(Color.WHITE);
        this.setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Txtname.setText(clientElem.getName());
        Txtip.setText(clientElem.getServerIP());
        Txtport.setText(String.valueOf(clientElem.getPort()));
        jbtxt.addActionListener(new sendAction());
        //添加列表监听器，在输入框中显示   @联系人说：
        jusernamelist.addListSelectionListener(new SelecTry());
        //设置按键监听，打开文件选择窗口
        jbpic.addActionListener(new sendFileAction());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    clientElem.getSocket().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args){
        new JComboBoxModel();
    }
}



