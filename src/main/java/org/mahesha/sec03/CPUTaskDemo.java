package org.mahesha.sec03;


import org.mahesha.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class CPUTaskDemo {

    public static final Logger log = LoggerFactory.getLogger(CPUTaskDemo.class);
    //public static final int TASKS_COUNT = 5;
    // As we increase the thread 2,3,4, or 5 times the available thread, time also would increase
    // 2,3,4 or 5 times respectively
    public static final int TASKS_COUNT = 3 * Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {

        log.info("Tasks Count: {}", TASKS_COUNT);
        
        for(int i = 0; i < 3; i++){
            long totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofVirtual()));
            log.info("Total time taken with Virtual {} ms", totalTimeTaken);
            totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofVirtual()));
            log.info("Total time taken with Platform {} ms", totalTimeTaken);
        }


    }

    private static void demo(Thread.Builder builder){

        CountDownLatch latch = new CountDownLatch(TASKS_COUNT);

        for (int i = 1; i <= TASKS_COUNT; i++) {
            builder.start(() -> {
                Task.cpuIntensive(45);
                latch.countDown();
            });
        }

        try{
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
