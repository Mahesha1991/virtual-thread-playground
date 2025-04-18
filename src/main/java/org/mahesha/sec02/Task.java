package org.mahesha.sec02;

import org.mahesha.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {

    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void execute(int i){
        log.info("Starting task {}", i);
        try{
            method1(i);
        }catch (Exception e){
            log.error("error for {}", i, e);
        }


    }

    public static void method1(int i){
        CommonUtils.sleep(Duration.ofMillis(200));

        try{
            method2(i);
        }catch(Exception e){
            throw new RuntimeException(e);
        }


    }

    public static void method2(int i){
        CommonUtils.sleep(Duration.ofMillis(100));

        method3(i);
    }

    public static void method3(int i){

        CommonUtils.sleep(Duration.ofMillis(500));
        if (i == 4){
            throw new IllegalArgumentException("I cannot be 4");
        }


    }
}
