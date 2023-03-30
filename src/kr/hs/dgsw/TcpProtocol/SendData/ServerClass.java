package kr.hs.dgsw.TcpProtocol.SendData;

import java.io.*;
import java.net.*;

public class ServerClass {
    public static void main(String[] args) {
        System.out.println("[SERVER]");
        int PORT = 2345;
        try{
            ServerSocket serverSocket = new ServerSocket();
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), PORT);
            serverSocket.bind(socketAddress);
            System.out.println("클라이언트 접속을 대기 중입니다.\nPORT : " + PORT);

            Socket socket = serverSocket.accept();
            System.out.println("클라이언트 연결 요청을 수락합니다.");
            System.out.println("접속된 클라이언트 주소는 " + socket.getInetAddress() + ":" + socket.getPort() +  "입니다.");

            // 원격 스트림 생성
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

            String str = dataInputStream.readUTF();
            System.out.println("클라이언트 : " + str);
            dataOutputStream.writeUTF("어서오세요. 클라이언트님");
            dataOutputStream.flush(); // 버퍼링으로 인해 기록되지 않은 데이터를 출력 스트림에 모두 출력

            socket.close();
            serverSocket.close();
        }catch (IOException e){
        }
    }
}
