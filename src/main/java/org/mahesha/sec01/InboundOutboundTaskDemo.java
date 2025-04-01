package org.mahesha.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {

    //private static final int MAX_PLATFORM = 1_000_000;
    //Ideally the above will not work pthread_create_failed for Unix. This will be different in different OS
    // -Xss option will help to adjust the stack size
    private static final int MAX_PLATFORM = 10;

    // For Virtual threads, we do not see out of memory threads. This is because it is like an illusion provided by java
    private static final int MAX_VIRTUAL = 10;

    public static void main(String[] args) throws InterruptedException {

        vitualThreadDemo();
        //platformThreadDemo2();

    }

    private static void platformThreadDemo(){

        for(int i = 0; i < MAX_PLATFORM; i++){

            int finalI = i;
            Thread thread = new Thread(() -> Task.ioIntensive(finalI));
            thread.start();
        }
    }

    private static void platformThreadDemo2(){

        Thread.Builder.OfPlatform builder = Thread.ofPlatform().name("mahesha", 1);
        for(int i = 0; i < MAX_PLATFORM; i++){

            int finalI = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensive(finalI));
            thread.start();
        }
    }

    private static void platformThreadDemo3() throws InterruptedException {

        //Deamon threads will be spawn and our main thread will exit if we do not add the latch
        CountDownLatch latch = new CountDownLatch(MAX_PLATFORM);

        Thread.Builder.OfPlatform builder = Thread.ofPlatform().daemon().name("daemon", 1);
        for(int i = 0; i < MAX_PLATFORM; i++){

            int finalI = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(finalI);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }



    // Create Virtual thread using Builder. Virtual Threads are Daemon Threads By Default.
    //Virtual Threads will not have any names by default
    private static void vitualThreadDemo() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(MAX_PLATFORM);

        Thread.Builder.OfVirtual builder = Thread.ofVirtual().name("virtual-", 1);
        for(int i = 0; i < MAX_VIRTUAL; i++){

            int finalI = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(finalI);
                latch.countDown();
            });
            thread.start();
        }

        latch.await();
    }
}
