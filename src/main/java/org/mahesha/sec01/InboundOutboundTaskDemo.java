package org.mahesha.sec01;

public class InboundOutboundTaskDemo {

    //private static final int MAX_PLATFORM = 1_000_000;
    //Ideally the above will not work pthread_create_failed for Unix. This will be different in different OS
    // -Xss option will help to adjust the stack size
    private static final int MAX_PLATFORM = 10;

    public static void main(String[] args) {

        platformThreadDemo();

    }

    private static void platformThreadDemo(){

        for(int i = 0; i < MAX_PLATFORM; i++){

            int finalI = i;
            Thread thread = new Thread(() -> Task.ioIntensive(finalI));
            thread.start();
        }

    }
}
