package org.wangep.listener.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/***
 * created by wange on 2020/9/8 14:11
 */
@Slf4j
@SpringBootApplication(scanBasePackageClasses = {AwaitingApplicationListener.class})
public class AwaitingApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final AtomicBoolean shutdownHookRegistered = new AtomicBoolean(false);
    private static final AtomicBoolean awaited = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        final SpringApplication springApplication = event.getSpringApplication();


        executorService.execute(() -> {

            synchronized (springApplication) {
                log.info(" [Dubbo] Current Spring Boot Application is await...");
                while (!awaited.get()) {
                    try {
                        springApplication.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        if (shutdownHookRegistered.compareAndSet(false, true)) {
            registerShutdownHook(new Thread(() -> {
                synchronized (springApplication) {
                    if (awaited.compareAndSet(false, true)) {
                        springApplication.notifyAll();
                        log.info(" [Dubbo] Current Spring Boot Application is about to shutdown...");
                        executorService.shutdown();
                    }
                }
            }));
        }
    }

    private void registerShutdownHook(Thread thread) {
        Runtime.getRuntime().addShutdownHook(thread);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(AwaitingApplicationListener.class).web(WebApplicationType.NONE).run(args);
    }

}
