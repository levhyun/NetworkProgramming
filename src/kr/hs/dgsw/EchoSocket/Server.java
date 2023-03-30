package kr.hs.dgsw.EchoSocket;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket server_socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        int PORT = 1234;

        try {
            server_socket = new ServerSocket(PORT);
        }catch(IOException e) {
            System.out.println("이미 열려있는 포트입니다.");
        }

        try {
            System.out.println("서버가 생성되었습니다.\nPORT : " + PORT);
            socket = server_socket.accept(); // Client 접속 요청 대기

            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 입력스트림
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); // 출력스트림

            while(true) {
                String str = in.readLine(); // Client로부터 수신한 데이터 읽기
                System.out.println("Client로부터 수신한 메세지 : " + str);

//                out.write(str);
                out.println(str);
                out.flush(); // 버퍼링으로 인해 기록되지 않은 데이터를 출력 스트림에 모두 출력
//                socket.close(); // 출력되지 않은 스트림은 모두 출력하고 스트림을 닫는다.
            }
        }catch(IOException e) {

        }
    }
}
