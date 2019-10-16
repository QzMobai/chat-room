package com.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

public class connectAction{
    clientelem clientValue = new clientelem();
    public void connect(){
        try {
            String serverIP = clientValue.getServerIP();
            int port = clientValue.getPort();
            Socket socket1 = new Socket(serverIP, port);
            clientValue.setSocket(socket1);
            clientValue.getBody().append("欢迎来到魔方小镇" + "\n");

            byte[] bytes = new byte[1024];
            DataOutputStream dos = new DataOutputStream(clientValue.getSocket().getOutputStream());
            clientValue.setSendName("SEVR".getBytes("Gbk"));
            clientValue.setStart("########".getBytes("Gbk"));
            clientValue.setEnd("********".getBytes("Gbk"));
            clientValue.setMessageOrFile("NAME".getBytes("Gbk"));
            clientValue.setToName("ALLA".getBytes("Gbk"));
            byte[] name = clientValue.getName().getBytes("Gbk");
            bytes = clientValue.Package(clientValue.getStart(), clientValue.getSendName(),
                    clientValue.getMessageOrFile(), clientValue.getToName(), name, clientValue.getEnd());
            dos.write(bytes);
            dos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Read r = new Read();
        Thread read = new Thread(r);
        read.start();
    }
}

class Read implements Runnable {
    clientelem clientValue = new clientelem();
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(clientValue.getSocket().getInputStream());
            while (true) {
                byte[] bytes = new byte[1024];
                in.read(bytes);
                int i = clientValue.getEnd(bytes,"########".getBytes("Gbk"),0);
                for (int j = 0; j < 4; j++) {
                    clientValue.getSendName()[j] = bytes[j + i];
                }
                String s1 = new String(clientValue.getSendName(),"Gbk");
                if (Objects.equals(s1, "SEVR")) {
                    s1 = "服务器";
                } else if (Objects.equals(s1, clientValue.getName())) {
                    s1 = "你";
                } else {
                    s1 = "<" + new String(clientValue.getSendName(),"Gbk") + ">";
                }
                for (int j = 0; j < 4; j++) {
                    clientValue.getToName()[j] = bytes[j + i + 4];
                }
                String s2 = new String(clientValue.getToName(),"Gbk");
                if (Objects.equals(s2, "ALLA")) {
                    s2 = "对大家说：";
                } else if (Objects.equals(s2,clientValue.getName())) {
                    s2 = "对你说：";
                } else {
                    s2 = "对<" + new String(clientValue.getToName(),"Gbk") + ">说：";
                }
                for (int j = 0; j < 4; j++) {
                    clientValue.getMessageOrFile()[j] = bytes[j + i + 8];
                }
                String s3 = new String(clientValue.getMessageOrFile(),"Gbk");
                if (Objects.equals(s3, "MESM")) {
                    int j=clientValue.getEnd(bytes,"********".getBytes("Gbk"),i+12)-8;
                    byte[] getMessage = new byte[j];
                    System.arraycopy(bytes, i+12, getMessage, 0, j);
                    String s4 = new String(getMessage,"Gbk");
                    clientValue.getBody().append(s1 + s2 + s4 + "\n");
                } else if (Objects.equals(s3, "LIST")) {
                    int j=clientValue.getEnd(bytes,"********".getBytes("Gbk"),i+12)-8;
                    byte[] getMessage = new byte[j];
                    System.arraycopy(bytes, i+12, getMessage, 0, j);
                    String namelist = new String(getMessage,"Gbk");
                    if(namelist != "") {
                        clientValue.getBody().append("更新用户列表！"+"\n");
                        String[] name = namelist.split(" ");
                        clientValue.setTotalName(name);
                        clientValue.setUserNameList();
                        for(int n = 0; n < name.length; n++) {
                            clientValue.getBody().append("用户：" + name[n] + "\n");
                        }
                    }
                }else if (Objects.equals(s3, "FILE")) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class RecToServer implements Runnable {
    Socket socket = null;
    public RecToServer(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            while (true) {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String namestr = dis.readUTF();
                if(namestr != "") {
                    new clientelem().getBody().append("更新用户列表！"+"\n");
                    String[] name = namestr.split(" ");
                    new clientelem().setTotalName(name);
                    new clientelem().setUserNameList();
                    for(int i = 0; i < name.length; i++) {
                        new clientelem().getBody().append("用户：" + name[i] + "\n");
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println("服务器已关闭");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
