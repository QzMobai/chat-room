package com.server;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class ServerGUI {
    JLabel LabelSendTo = new serverelem().getLabelSendTo();
    private JTextArea body = new serverelem().getBody();
    // 创建文字域
    private JScrollPane roll = new JScrollPane(body);
    // 在文本框上添加滚动条

    public void creatWindow() {
        JFrame window = new JFrame("服务器");
        Container container = window.getContentPane();
        // 获取一个容器
        container.setLayout(new GridLayout(4, 1, 0, 10));
        // 设置网格，参数依次为（行，列，长度，宽度）
        LabelSendTo = new JLabel("开放端口：");
        // 创建标签
        JButton start = new JButton("开放");
        // 创建按钮
        body.setLineWrap(true);
        // 设置自动换行
        roll.setBounds(0, 0, 50, 50);
        // 设置矩形大小，参数依次为（左上角x，左上角y，长度，宽度）
        roll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // 设置让滚动条一直显示
        container.add(LabelSendTo);
        container.add(roll);
        container.add(start);
        // 按顺序加入容器
        window.setVisible(true);
        // 使窗体可见
        window.setSize(500, 350);
        // 设置窗体大小
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 设置窗体关闭方式
        start.addActionListener(new GetAction());
        // 为按钮设置动作响应

    }
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        // 设置为ipv4
        new ServerGUI().creatWindow();
    }
}
