package kr.hs.dgsw.ChatingService;

import java.io.*;
import java.net.*;

public class Client {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private String username;
    private InetSocketAddress socketAddress;
    private String notif = " *** ";
    private boolean keepGoing;

    public Client(InetSocketAddress socketAddress, String username) {
        this.socketAddress = socketAddress;
        this.username = username;
    }

    public boolean start() {
        keepGoing = true;
        try {
            socket = new Socket(socketAddress.getAddress(), socketAddress.getPort());
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                while (keepGoing) {
                    try {
                        String msg = inputStream.readUTF();
                        System.out.println(msg);
                        System.out.print("> ");
                    } catch (IOException e) {
                        display(notif + " 서버 종료 " + notif);
                        break;
                    }
                }
            }
        }.start();

        try {
            outputStream.writeUTF(username);
            outputStream.flush();
        } catch (IOException e) {
            display("이름 전송 예외 " + e);
            disconnect();
            return false;
        }
        return true;
    }

    public void sendMessage(ChatMessage msg) {
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
        } catch (IOException e) {
            display("전송 예외:" + e);
        }
    }

    public void disconnect() {
        try {
            keepGoing = false;
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
        }
    }

    private void display(String msg) {
        System.out.println(msg);
    }
}
