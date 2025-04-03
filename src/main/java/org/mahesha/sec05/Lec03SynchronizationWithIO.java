package org.mahesha.sec05;

import org.mahesha.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/*
    Virtual Threads are indented for I/O tasks. This is a simple demo to show that race conditions are still applicable.
 */

public class Lec03SynchronizationWithIO {

    private static final Logger log = LoggerFactory.getLogger(Lec01RaceCondition.class);
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {

        demo(Thread.ofPlatform());
        log.info("list size: {}", list.size());

    }

    private static void demo(Thread.Builder builder){
        CountDownLatch latch = new CountDownLatch(50);
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Task started. {}", Thread.currentThread());
                ioTask();
                log.info("Task ended. {}", Thread.currentThread());
                latch.countDown();
            });
        }
        try{
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static synchronized void ioTask(){
        list.add(1);
        CommonUtils.sleep(Duration.ofSeconds(10));
    }

}