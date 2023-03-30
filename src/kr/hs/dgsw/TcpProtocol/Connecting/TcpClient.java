package kr.hs.dgsw.TcpProtocol.Connecting;

import java.io.*;
import java.net.*;

public class TcpClient {
    public static void main(String[] args) {
        Socket socket = null;
        try{
            socket = new Socket(InetAddress.getLoopbackAddress(), 12345);
            System.out.println("Connecting Server...");
            System.out.println("Coonnecting\nServer Address =  " + socket.getInetAddress() + ":" + socket.getPort());
            socket.close();
        }catch (IOException e){
        }
    }
}