package com.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class serverelem {
    private static int index = 0;
    private static boolean flag = false;
    private static byte[] start = new byte[8];
    private static byte[] sendName = new byte[4];
    private static byte[] messageOrFile = new byte[4];
    private static byte[] toName = new byte[4];
    private static byte[] end = new byte[8];
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static String[] client = new String[20];
    private static JLabel LabelSendTo;
    private static JTextArea body = new JTextArea();
    private static List<Socket> clientSocketList = new ArrayList<Socket>();
    private static List<String> clientNameList = new ArrayList<String>();
    private Map<Socket, String> clientlist = new HashMap<>();

    public int getIndex() {
        return index;
    }
    public void setIndex(int newIndex) {
        index = newIndex;
    }
    public boolean getFlag() {
        return flag;
    }
    public void setFlag(boolean newFlag) {
        flag = newFlag;
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
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket obj) {
        socket = obj;
    }
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    public void setServerSocket(ServerSocket obj) {
        serverSocket = obj;
    }
    public String[] getClient() {
        return client;
    }
    public void setClient(String[] obj) {
        client = obj;
    }
    public JLabel getLabelSendTo() {
        return LabelSendTo;
    }
    public void setLabelSendTo(JLabel obj) {
        LabelSendTo = obj;
    }
    public JTextArea getBody() {
        return body;
    }
    public void setBody(JTextArea obj) {
        body = obj;
    }
    public List<Socket> getSocketList(){
        return clientSocketList;
    }
    public List<String> getNameList(){
        return clientNameList;
    }
    public Map<Socket,String> getClientList(){
        return clientlist;
    }

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
     * @param name
     * @return
     */
    public Socket positon(String name) {
        Socket positon = null;
        for(Socket key: clientlist.keySet()) {
            if (Objects.equals(clientlist.get(key), name)) {
                positon = key;
                break;
            }
        }
        return positon;
    }

    /**
     *
     * @param data
     * @return
     */
    public static int ActualLength(byte[] data) {
        int i = data.length;
        for (; i > 0; i--) {
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
