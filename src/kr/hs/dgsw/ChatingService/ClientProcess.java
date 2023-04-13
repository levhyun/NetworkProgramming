package kr.hs.dgsw.ChatingService;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class ClientProcess {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userName = "";
        int port = 1234;
        System.out.print("이름을 입력하세요 : ");
        userName = scanner.nextLine();

        InetAddress inetAddress = InetAddress.getLoopbackAddress();
        InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port);

        Client client = new Client(socketAddress, userName);
        if (!client.start()) return;

        System.out.println("\n안녕하세요. 오픈 채팅방에 오신 것을 환영합니다.");
        System.out.println("사용 방법");
        System.out.println("\t1. 기본 메시지의 경우 모든 유저에게 전달됩니다.");
        System.out.println("\t2. 개인 메시지의 경우 ‘@UserName<space>메시지' ex) @User1 안녕하세요.");
        System.out.println("\t3. 로그아웃 시 'LOGOUT'을 입력");
        System.out.println();

        while (true) {
            System.out.print("> ");
            String msg = scanner.nextLine();
            if (msg.equalsIgnoreCase("LOGOUT")) {
                client.sendMessage(new ChatMessage(ChatMessageType.LOGOUT, ""));
                System.out.println("로그아웃");
                break;
            } else {
                client.sendMessage(new ChatMessage(ChatMessageType.MESSAGE, msg));
            }
        }
        client.disconnect();
        scanner.close();
    }
}
