package kr.hs.dgsw;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetAddressObject {

    public static void main(String[] args) throws IOException {
        // InetAddress
        System.out.println("\n[InetAddress]");
        try {
            // 1. InetAddress 객체 생성

            // 1-1. 원격지IP 객체 생성
            InetAddress ia1 = InetAddress.getByName("www.google.com"); // Host이름과 해당 IP주소 저장 객체 리턴
            byte[] googleIP = new byte[]{(byte)172, (byte)217, (byte)25, (byte)164}; // byte 배열 생성
            InetAddress ia2 = InetAddress.getByAddress(googleIP); // 입력 IP주소 저장 객체 리턴(128이상의 경우 (byte)캐스팅 필요)
            InetAddress ia3 = InetAddress.getByAddress("www.google.com", new byte[]{(byte)172, (byte)217, (byte)161, 36}); // Host이름과 입력 IP주소 저장 객체 리턴(128이상의 경우 (byte)캐스팅 필요)
            System.out.println("ia1 = " + ia1);
            System.out.println("ia2 = " + ia2);
            System.out.println("ia3 = " + ia3);

            // 1-2. 로컬/로프백IP
            InetAddress ia4 = InetAddress.getLocalHost(); // 로컬 호스트 IP주소 저장 객체 리턴
            InetAddress ia5 = InetAddress.getLoopbackAddress(); // 루프백 IP 주소 저장 객체 리턴
            System.out.println("ia4 = " + ia4);
            System.out.println("ia5 = " + ia5);


            // 2. InetAddress 메서드
            byte[] address = ia1.getAddress(); // InetAddress 객체가 저장하고 있는 IP주소를 byte[]로 리턴
            System.out.println("Arrays.toString(address) = " + Arrays.toString(address));
            System.out.println("ia1.getHostAddress() = " + ia1.getHostAddress()); // InetAddress 객체가 저장하고 있는 IP주소를 문자열로 리턴
            System.out.println("ia1.getHostName() = " + ia1.getHostName()); // InetAddress 객체가 저장하고 있는 호스트의 이름을 문자열로 리턴
            System.out.println("ia2.isReachable(1000) = " + ia2.isReachable(1000)); // Ping 명령으로 리턴 여부 확인
            System.out.println("ia1.isLoopbackAddress() = " + ia1.isLoopbackAddress()); // IP가 루프백 주소인지 확인

            System.out.println("InetAddress.getByAddress(new byte[] {127,0,0,1}).isLoopbackAddress() = " + InetAddress.getByAddress(new byte[] {127,0,0,1}).isLoopbackAddress()); //true
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        // InetSocketAddress
        System.out.println("\n[InetSocketAddress]");
        try {
            // 1. SocketAddress 객체 생성
            InetAddress address = InetAddress.getByName("www.google.com");
            int port = 10000;
            InetSocketAddress isa1 = new InetSocketAddress(port); // IP 주소 없이 내부의 포트 정보만 지정
            InetSocketAddress isa2 = new InetSocketAddress("www.google.com", port); // 매개변수의 호스트의 이름에 해당하는 IP와 포트 번호를 지정
            InetSocketAddress isa3 = new InetSocketAddress(address, port); // 매개변수의 InetAddress(IP 정보)와 포트 번호를 지정
            System.out.println("isa1 = " + isa1);
            System.out.println("isa2 = " + isa2);
            System.out.println("isa3 = " + isa3);

            // 2. SocketAddress의 메서드
            System.out.println("isa2.getAddress() = " + isa2.getAddress()); // 저장 IP 주소를 InetAddress 타입을 리턴
            System.out.println("isa2.getHostName() = " + isa2.getHostName()); // 호스트의 이름을 문자열로 리턴
            System.out.println("isa2.getPort() = " + isa2.getPort()); // 포트 번호를 정수형으로 리턴
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
