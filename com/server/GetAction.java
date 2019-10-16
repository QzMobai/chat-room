package com.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GetAction implements ActionListener {
    serverelem serverValue = new serverelem();
    public void actionPerformed(ActionEvent arg0) {
        serverValue.getBody().append("端口8888已开放"+"\n");
        Count c = new Count();
        Thread count1 = new Thread(c);
        count1.start();
    }
}

class Count implements Runnable {
    serverelem serverValue = new serverelem();
    @Override
    public void run() {
        Serverserver t = new Serverserver(8888);
        while (true) {
            try {
                serverValue.setSocket(serverValue.getServerSocket().accept());
                serverValue.getSocketList().add(serverValue.getSocket());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread read = new Thread(t);
            read.start();
        }
    }
}
