package kr.hs.dgsw.ChatingService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {
    private static int uniqueId;
    private ArrayList<CilentThread> clientThreads;
    private SimpleDateFormat simpleDateFormat;
    private int port;
    private boolean isRunning;
    private String notif = " *** ";

    public Server(int port) {
        this.port = port;
        this.simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        this.clientThreads = new ArrayList<>();
    }

    public void start() {
        isRunning = true;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            display("[서버 실행]");
            while (isRunning) {
                Socket socket  = serverSocket.accept();
                if(!isRunning) break;
                CilentThread clientThread = new CilentThread(socket);
                clientThreads.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            display("서버 예외 발생 " + e);
        }
        System.out.println("서버 종료");
    }

    public void stop() {
        isRunning = false;
    }

    private void display(String message) {
        String time = simpleDateFormat.format(new Date()) + " " + message;
        System.out.println(time);
    }

    private synchronized boolean broadcast(String message) {
        String time = simpleDateFormat.format(new Date());
        String msg = time + " " + message;
        for (int i = clientThreads.size()-1; i >= 0; i--) {
            CilentThread cilentThread = clientThreads.get(i);
            if (!cilentThread.writeMsg(msg)) {
                clientThreads.remove(i);
                display("Client: " + cilentThread.userName + "연결 종료");
            }
        }
        return true;
    }

    private synchronized void remove(int id) {
        String disconnectedClient = "";
        for (CilentThread cilentThread : clientThreads) {
            if (cilentThread.id == id) {
                disconnectedClient = cilentThread.getUserName();
                clientThreads.remove(cilentThread);
                break;
            }
        }
        broadcast(notif + disconnectedClient + "님이 채팅방을 나갔습니다." + notif);
    }

    public class CilentThread extends Thread{
        Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;
        int id;
        String userName;
        ChatMessage chatMessage;
        boolean keepGoing;

        public CilentThread(Socket socket) {
            id = ++uniqueId;
            keepGoing = true;
            this.socket = socket;
            display("클라이언트 " + socket.getInetAddress() + ":" + socket.getPort() + "접속");
            try {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                userName = inputStream.readUTF();
                broadcast(notif + userName + "님이 채팅방을 들어오셧습니다." + notif);
            } catch (IOException e) {
                display(" " + e);
                return;
            }
        }

        @Override
        public void run() {
            while (keepGoing) {
                try {
                    chatMessage = (ChatMessage) inputStream.readObject();
                } catch (IOException e) {
                    display(userName + "님 예외 발생" + e);
                } catch (ClassNotFoundException e) {
                    break;
                }
                String message = chatMessage.getMessage();
                switch (chatMessage.getType()) {
                    case MESSAGE:
                        broadcast(userName + " : " + message);
                        break;
                    case LOGOUT:
                        display(userName + " 로그아웃.");
                        keepGoing = false;
                        break;
                }
            }
            remove(id);
            close();
        }

        private boolean writeMsg(String msg) {
            if (!socket.isConnected()) {
                close();
                return false;
            }
            try {
                outputStream.writeUTF(msg);
                outputStream.flush();
            } catch (IOException e) {
                display(notif + "메세지 전송 에러" + userName + notif);
                display(e.toString());
            }
            return true;
        }

        public void close() {
            try {
                if (outputStream != null) outputStream.close();
                if (inputStream != null) inputStream.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
            }
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
