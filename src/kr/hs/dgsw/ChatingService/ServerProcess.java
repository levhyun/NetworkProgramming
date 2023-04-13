package kr.hs.dgsw.ChatingService;

public class ServerProcess {
    public static void main(String[] args) {
        int port = 1234;
        new Server(port).start();
    }
}
