package com.client3;

        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

        import javax.swing.JButton;
        import javax.swing.JDialog;
        import javax.swing.JLabel;
        import javax.swing.JOptionPane;
        import javax.swing.JPasswordField;
        import javax.swing.JTextField;

        import com.client.*;

public  class logininterface extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField namebox;
    private JLabel namelab,passwordlab,loginlab;
    private JPasswordField passwordbox;
    public logininterface() {
        Container container1 = getContentPane();
        setLayout(null);
        setBounds(200,200,350,500);
        namelab = new JLabel("用户名:");
        namelab.setFont(new Font("黑体",Font.PLAIN,15));
        passwordlab = new JLabel("密  码:");
        passwordlab.setFont(new Font("黑体",Font.PLAIN,15));
        loginlab = new JLabel("登 录");
        namelab.setBounds(50,130,60,40);
        passwordlab.setBounds(50,180,60,40);
        loginlab.setBounds(150,20,150,90);
        container1.add(namelab);
        container1.add(passwordlab);
        container1.add(loginlab);
        loginlab.setFont(new Font("黑体",Font.PLAIN,36));loginlab.setForeground(Color.blue);


        namebox = new JTextField();

        namebox.setHorizontalAlignment(JTextField.CENTER);
        passwordbox = new JPasswordField();
        passwordbox.setEchoChar('*');//设置回显字符
        passwordbox.setHorizontalAlignment(JTextField.CENTER);

        namebox.setBounds(120,130,150,40);
        passwordbox.setBounds(120,180,150,40);
        JButton bl = new JButton("登录");
        bl.setBounds(120,230,150,30);
        bl.setForeground(Color.white);
        bl.setBackground(Color.blue);
        container1.add(namebox);
        container1.add(passwordbox);
        container1.add(bl);
        setVisible(true);

        bl.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            @Override
            public  void actionPerformed(ActionEvent e) {
                if(namebox.getText().equals("")){
                    popWindows("请输入用户名", "ERROR");
                }else{
                    if(passwordbox.getText().equals("")){
                        popWindows("请输入你的密码", "ERROR");
                    }else{
                        if(passwordbox.getText().equals("1")){
                            popWindows("登录成功", "登录");
                            String name = namebox.getText();
                            if(namebox.getText().length() < 4) {
                                for(int i =name.length()-1; i<4; i++) {
                                    name += " ";
                                }
                            }
                            new clientelem().setName(name);
                            new JComboBoxModel();
                            new connectAction().connect();
                            setVisible(false);
                        } else {
                            popWindows("登录失败，密码不正确", "ERROR");
                            passwordbox.setText("");
                        }
                    }
                }
            }
        });
    }
    public void popWindows(String strWarning, String strTitle) {
        JOptionPane.showMessageDialog(this, strWarning, strTitle,
                JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(String[] args) {
        new logininterface();
    }



}

