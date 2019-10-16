package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;

public class sendAction implements ActionListener {
    // 重写send按钮的动作响应
    public void actionPerformed(ActionEvent arg0) {
        send t = new send();
        Thread print = new Thread(t);
        print.start();
    }
}

class send implements Runnable {
    clientelem clientValue = new clientelem();
    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(clientValue.getSocket().getOutputStream());
            clientValue.setSendName(clientValue.getName().getBytes());
            clientValue.setStart("########".getBytes("Gbk"));
            clientValue.setEnd("********".getBytes("Gbk"));
            clientValue.setMessageOrFile("MESM".getBytes("Gbk"));
            byte[] message = new byte[996];
            if (clientValue.getMessage().getText().contains("@")) {
                byte[] toName1 = clientValue.getMessage().getText().split("@")[0].getBytes("Gbk");
                clientValue.setToName(toName1);
                message = clientValue.getMessage().getText().split("@")[1].getBytes("Gbk");
                byte[] bytes = clientValue.Package(clientValue.getStart(), clientValue.getSendName(),
                        clientValue.getMessageOrFile(), clientValue.getToName(), message, clientValue.getEnd());
                out.write(bytes);
                out.flush();
            } else {
                clientValue.setToName("ALLA".getBytes("Gbk"));
                message = clientValue.getMessage().getText().getBytes("Gbk");
                byte[] bytes = clientValue.Package(clientValue.getStart(), clientValue.getSendName(),
                        clientValue.getMessageOrFile(), clientValue.getToName(), message, clientValue.getEnd());
                out.write(bytes);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
