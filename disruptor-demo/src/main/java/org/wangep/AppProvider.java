package org.wangep;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wangep.log.LogProvider;
import org.wangep.log.publisher.AppendPublisher;

import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 */
public class AppProvider {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-provider.xml", "spring/spring.xml");
        context.start();

        LogProvider.initPublisher(context.getBean("appendPublisher", AppendPublisher.class));
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }

}
