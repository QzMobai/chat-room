package com.client;

import java.net.Socket;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class clientelem {
    private static int serverPort = 8888;
    private static String name;
    private static String serverIP = "localhost";
    private static String[] totalName;
    private static Socket socket;
    private static byte[] start = new byte[8];
    private static byte[] sendName = new byte[4];
    private static byte[] messageOrFile = new byte[4];
    private static byte[] toName = new byte[4];
    private static byte[] end = new byte[8];
    private static JTextField postTo = new JTextField(null);
    private static JTextField message = new JTextField(null);
    private static JTextField Filetext = new JTextField(null);
    private static JTextArea body = new JTextArea();
    private static JList<String> usernamelist = new JList<String>();

    public String getName() {
        return name;
    }
    public void setName(String obj) {
        name = obj;
    }
    public String[] getTotalName() {
        return totalName;
    }
    public void setTotalName(String[] obj) {
        totalName = obj;
    }
    public int getPort() {
        return serverPort;
    }
    public void setPort(int obj) {
        serverPort = obj;
    }

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket obj) {
        socket = obj;
    }
    public String getServerIP() {
        return serverIP;
    }
    public void setServerIP(String obj) {
        serverIP = obj;
    }
    public byte[] getStart() {
        return start;
    }
    public void setStart(byte[] obj) {
        start = obj;
    }
    public byte[] getSendName() {
        return sendName;
    }
    public void setSendName(byte[] obj) {
        sendName = obj;
    }
    public byte[] getMessageOrFile() {
        return messageOrFile;
    }
    public void setMessageOrFile(byte[] obj) {
        messageOrFile = obj;
    }
    public byte[] getToName() {
        return toName;
    }
    public void setToName(byte[] obj) {
        toName = obj;
    }
    public byte[] getEnd() {
        return end;
    }
    public void setEnd(byte[] obj) {
        end = obj;
    }
    public JTextField getPostTo() {
        return postTo;
    }
    public void setPostTo(JTextField obj) {
        postTo = obj;
    }
    public JTextField getMessage() {
        return message;
    }
    public void setmessage(JTextField obj) {
        message = obj;
    }
    public JTextField getFiletext() {
        return Filetext;
    }
    public void setFiletext(JTextField obj) {
        Filetext = obj;
    }
    public JTextArea getBody() {
        return body;
    }
    public void setBody(JTextArea obj) {
        body = obj;
    }
    public JList<String> getUserNameList(){
        return usernamelist;
    }
    public void setUserNameList(JList<String> obj) {
        usernamelist = obj;
    }
    public void setUserNameList() {
        usernamelist.setListData(totalName);
    }

    /**
     *
     * @param bytes
     * @param start
     * @param j
     * @return
     */
    public int getEnd(byte[]bytes,byte[]start,int j){
        int i ;
        byte[] temp = new byte[1024];;
        for (i = 0; ; i++) {
            temp[i] = bytes[i+j];
            if (i > 6
                    && temp[i] == start[7]
                    && temp[i - 1] == start[6]
                    && temp[i - 2] == start[5]
                    && temp[i - 3] == start[4]
                    && temp[i - 4] == start[3]
                    && temp[i - 5] == start[2]
                    && temp[i - 6] == start[1]
                    && temp[i - 7] == start[0]) {
                i++;
                break;
            }
        }
        return i;
    }

    /**
     *
     * @param data
     * @return
     */
    public int ActualLength(byte[] data) {
        int i = data.length;
        for (; i>0; i--) {
            if (data[i-1] != 0) {
                break;
            }
        }
        return i;
    }

    /**
     * int整数转换为4字节的byte数组
     *
     * @param i 整数
     * @return byte数组
     */
    public byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }

    /**
     * byte数组转换为int整数
     *
     * @param bytes byte数组
     * @return int整数
     */
    public int byte4ToInt(byte[] bytes) {
        int b0 = bytes[0] & 0xFF;
        int b1 = bytes[1] & 0xFF;
        int b2 = bytes[2] & 0xFF;
        int b3 = bytes[3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }

    /**
     *
     * @param start
     * @param sendName
     * @param messageOrFile
     * @param toName
     * @param message
     * @param end
     * @return
     */
    public byte[] Package(byte[] start,
                          byte[] sendName,
                          byte[] messageOrFile,
                          byte[] toName,
                          byte[] message,
                          byte[] end) {
        byte[] bytes = new byte[1024];
        System.arraycopy(start, 0, bytes, 0, 8);
        System.arraycopy(sendName, 0, bytes, 8, 4);
        System.arraycopy(toName, 0, bytes, 12, 4);
        System.arraycopy(messageOrFile, 0, bytes, 16, 4);
        System.arraycopy(message, 0, bytes, 20, ActualLength(message));
        System.arraycopy(end, 0, bytes, 20 + ActualLength(message), 8);
        return bytes;
    }
}
