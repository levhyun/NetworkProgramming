package kr.hs.dgsw.TcpProtocol.SendData;

import java.io.*;
import java.net.*;

public class ClinetClass {
    public static void main(String[] args) {
        System.out.println("[CLIENT]");
        int PORT = 2345;
        try{
            Socket socket = new Socket(InetAddress.getLoopbackAddress(), PORT);
            System.out.println("서버에 접속 요청을 보냅니다.");
            System.out.println("접속한 서버의 주소는 " + socket.getInetAddress() + ":" + socket.getPort() + " 입니다.");

            // 원격 스트림 생성
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

            dataOutputStream.writeUTF("안녕하세요. 서버님");
            dataOutputStream.flush(); // 버퍼링으로 인해 기록되지 않은 데이터를 출력 스트림에 모두 출력

            String str = dataInputStream.readUTF();
            System.out.println("서버 : " + str);

            socket.close();
        }catch (IOException e){
        }
    }
}
