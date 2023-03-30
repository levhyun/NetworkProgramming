package kr.hs.dgsw.TcpProtocol.Connecting;

import java.io.*;
import java.net.*;

public class TcpServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 12345)); // InetAddress.getLoopbackAddress() : 127.0.0.1
            System.out.println(serverSocket.isBound() + "\nlistening to client...");
//            serverSocket.setSoTimeout(2000);
            Socket socket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("timeout!");
        }
        System.out.println("quit");
    }
}
