package com.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Serverserver implements Runnable {
    serverelem serverValue = new serverelem();
    public Serverserver(int port) {
        // 构造方法
        try {
            serverValue.setServerSocket(new ServerSocket(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 重写run方法
        try {
            DataInputStream in = new DataInputStream(serverValue.getSocket().getInputStream());
            while (true) {
                byte[] bytes = new byte[1024];
                in.read(bytes);
                int i = 0;
                byte[] temp = new byte[1024];
                serverValue.setStart("########".getBytes("Gbk"));
                for (int j = 0; ; j++, i++) {
                    temp[j] = bytes[i];
                    if (j > 6
                            && temp[j] == serverValue.getStart()[7]
                            && temp[j - 1] == serverValue.getStart()[6]
                            && temp[j - 2] == serverValue.getStart()[5]
                            && temp[j - 3] == serverValue.getStart()[4]
                            && temp[j - 4] == serverValue.getStart()[3]
                            && temp[j - 5] == serverValue.getStart()[2]
                            && temp[j - 6] == serverValue.getStart()[1]
                            && temp[j - 7] == serverValue.getStart()[0]) {
                        i++;
                        break;
                    }
                }

                for (int j = 0; j < 4; j++) {
                    serverValue.getMessageOrFile()[j] = bytes[j + i + 8];
                }

                String s1 = new String(serverValue.getMessageOrFile(),"Gbk");

                if (Objects.equals(s1, "FILE")) {
                    serverValue.setEnd("********".getBytes("Gbk"));
                    for (int j = 0; ; j++) {
                        temp[j] = bytes[j + i + 12];
                        if (j > 6
                                && temp[j] == serverValue.getEnd()[7]
                                && temp[j - 1] == serverValue.getEnd()[6]
                                && temp[j - 2] == serverValue.getEnd()[5]
                                && temp[j - 3] == serverValue.getEnd()[4]
                                && temp[j - 4] == serverValue.getEnd()[3]
                                && temp[j - 5] == serverValue.getEnd()[2]
                                && temp[j - 6] == serverValue.getEnd()[1]
                                && temp[j - 7] == serverValue.getEnd()[0]) {
                            i = j - 7;
                            break;
                        }
                    }
                    byte[] getMessage = new byte[i];
                    System.arraycopy(temp, 0, getMessage, 0, i);
                    byte[] bend = "@@@@@@@@".getBytes("Gbk");
                    for (int j = 0; ; j++) {
                        temp[j] = getMessage[j];
                        if (j > 6
                                && temp[j] == bend[7]
                                && temp[j - 1] == bend[6]
                                && temp[j - 2] == bend[5]
                                && temp[j - 3] == bend[4]
                                && temp[j - 4] == bend[3]
                                && temp[j - 5] == bend[2]
                                && temp[j - 6] == bend[1]
                                && temp[j - 7] == bend[0]) {
                            i = j - 7;
                            break;
                        }
                    }
                    byte[] v = new byte[i];
                    System.arraycopy(getMessage, 0, v, 0, i);
                    String name = new String(v,"Gbk");
                    name = name + "副本";
                    byte[] f = new byte[(getMessage.length - 8 - i)];
                    System.arraycopy(getMessage, (i + 8), f, 0, (getMessage.length - 8 - i));
                    File file = new File(name);
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    FileOutputStream writer = new FileOutputStream(name, true);
                    writer.write(f);
                    writer.close();
                }

                if (Objects.equals(s1, "MESM")) {
                    for (int j = 0; j < 4; j++) {
                        serverValue.getSendName()[j] = bytes[j + i];
                    }
                    String sName = new String(serverValue.getSendName(),"Gbk");
                    for (int j = 0; j < 4; j++) {
                        serverValue.getToName()[j] = bytes[j + i + 4];
                    }
                    String s2 = new String(serverValue.getToName(),"Gbk");
                    if (Objects.equals(s2, "ALLA")) {
                        for (int j = 0; j < serverValue.getSocketList().size(); j++) {
                            Socket socket = serverValue.getSocketList().get(j);
                            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                            out.write(bytes);
                            out.flush();
                        }
                    } else {
                        String name = new String(serverValue.getToName(),"Gbk");
                        if (serverValue.positon(name) != null) {
                            Socket socket1 = serverValue.positon(name);
                            DataOutputStream out1 = new DataOutputStream(socket1.getOutputStream());
                            out1.write(bytes);
                            out1.flush();
                            Socket socket2 = serverValue.positon(sName);
                            DataOutputStream out2 = new DataOutputStream(socket2.getOutputStream());
                            out2.write(bytes);
                            out2.flush();
                        } else {
                            Socket socket2 = serverValue.positon(sName);
                            DataOutputStream out2 = new DataOutputStream(socket2.getOutputStream());
                            serverValue.setSendName("SEVR".getBytes("Gbk"));
                            serverValue.setStart("########".getBytes("Gbk"));
                            serverValue.setEnd("********".getBytes("Gbk"));
                            serverValue.setMessageOrFile("MESM".getBytes("Gbk"));
                            serverValue.setToName(sName.getBytes());
                            byte[] message = "该用户不存在或未上线".getBytes("Gbk");
                            bytes = serverValue.Package(serverValue.getStart(), serverValue.getSendName(),
                                    serverValue.getMessageOrFile(), serverValue.getToName(), message, serverValue.getEnd());
                            out2.write(bytes);
                            out2.flush();
                        }
                    }
                }
                if(Objects.equals(s1, "NAME")) {
                    String namelist = "";
                    int j=serverValue.getEnd(bytes,"********".getBytes("Gbk"),8+12)-8;
                    byte[] getMessage = new byte[j];
                    System.arraycopy(bytes, 8+12, getMessage, 0, j);
                    String name = new String(getMessage,"Gbk");
                    serverValue.getClientList().put(serverValue.getSocket(), name);
                    serverValue.getBody().append("在线用户:\n");
                    for(Socket key : serverValue.getClientList().keySet()) {
                        serverValue.getBody().append("port: " + key.getPort() + " ");
                        serverValue.getBody().append("name: "+ serverValue.getClientList().get(key) + "\n");
                        namelist += serverValue.getClientList().get(key) + " ";
                    }
                    for (int n = 0; n < serverValue.getSocketList().size(); n++) {
                        Socket socket = serverValue.getSocketList().get(n);
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        serverValue.setSendName("SEVR".getBytes("Gbk"));
                        serverValue.setStart("########".getBytes("Gbk"));
                        serverValue.setEnd("********".getBytes("Gbk"));
                        serverValue.setMessageOrFile("LIST".getBytes("Gbk"));
                        serverValue.setToName("ALLA".getBytes("Gbk"));
                        byte[] nameList = namelist.getBytes("Gbk");
                        bytes = serverValue.Package(serverValue.getStart(), serverValue.getSendName(),
                                serverValue.getMessageOrFile(), serverValue.getToName(), nameList, serverValue.getEnd());
                        dos.write(bytes);
                        dos.flush();
                        serverValue.getBody().append("用户列表更新，发送给端口："+socket.getPort()+ "\n");
                    }
                    namelist = "";
                }
            }
        } catch (
                Exception e) {

            e.printStackTrace();
        }
    }
}
