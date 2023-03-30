package kr.hs.dgsw.EchoSocket;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        Socket socket = null; // Server와 통신하기 위한 Socket
        BufferedReader in = null; // Server로부터 데이터를 수신할 입력스트림
        BufferedReader in2 = null; // 키보드로부터 읽어들이기 위한 입력스트림
        PrintWriter out = null; // 서버로 송신하기 위한 출력 스트림
        InetAddress ia = null;

        try {
            ia = InetAddress.getLocalHost(); // 서버로 접속하기 위해 서버 주소 입력
            socket = new Socket(ia, 1234);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 입력스트림
            in2 = new BufferedReader(new InputStreamReader(System.in)); // 입력스트림
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); // 출력스트림
            System.out.println(socket.toString());
        }catch(IOException e) {

        }

        try {
            while(true) {
                System.out.print("서버로 송신할 매세지 : ");
                String data = in2.readLine();
                out.println(data);
                out.flush(); // 버퍼링으로 인해 기록되지 않은 데이터를 출력 스트림에 모두 출력

                String str2 = in.readLine(); // 서버로부터 데이터를 수신
                System.out.println("서버로부터 수신한 메세지 : " + str2);
//                socket.close();
            }
        }catch(IOException e) {

        }
    }
}
