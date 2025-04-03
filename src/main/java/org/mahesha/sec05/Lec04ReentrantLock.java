package org.mahesha.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    Virtual Threads are indented for I/O tasks. This is a simple demo to show that race conditions are still applicable.
 */

public class Lec04ReentrantLock {

    private static final Logger log = LoggerFactory.getLogger(Lec01RaceCondition.class);
    public static final Lock lock = new ReentrantLock();
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {

        demo(Thread.ofVirtual());
        log.info("list size: {}", list.size());

    }

    private static void demo(Thread.Builder builder){
        CountDownLatch latch = new CountDownLatch(50);
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Task started. {}", Thread.currentThread());
                for (int j = 0; j < 20; j++) {
                    inMemoryTask();
                }
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

    private static void inMemoryTask(){
        try{
            lock.lock();
            list.add(1);
        }catch (Exception e){
            log.error("Error", e);
        }finally {
            lock.unlock();
        }
    }

}