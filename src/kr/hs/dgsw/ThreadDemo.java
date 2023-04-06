package kr.hs.dgsw;

public class ThreadDemo {
    public static void main(String[] args) {
        // 인터페이스
//        Thread t = new Thread(new MyRunnable());
//        t.start();

        // 임명함수
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 5; i++) {
//                    System.out.print("잘가. ");
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException ignored) {}
//                }
//            }
//        }).start();

        // 람다식
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.print("잘가. ");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        }).start();

        for (int i = 0; i < 5; i++) {
            System.out.print("안녕. ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.print("잘가. ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }
    }
}
