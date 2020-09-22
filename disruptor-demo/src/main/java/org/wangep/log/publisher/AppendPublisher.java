package org.wangep.log.publisher;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.wangep.log.event.AppendEvent;
import org.wangep.log.factory.AppendEventFactory;
import org.wangep.log.factory.AppendThreadFactory;
import org.wangep.log.handler.AppendHandler;
import org.wangep.log.translator.AppendEventTranslator;

/***
 * created by wange on 2020/4/27 14:56
 */
public class AppendPublisher implements InitializingBean, DisposableBean {

    private int threadSize = 16;
    private int bufferSize = 1024 * 1024;
    private String filePath = "D:\\test\\log.txt";
    private Disruptor<AppendEvent> disruptor;


    private void start() {
        this.disruptor = new Disruptor<>(
                new AppendEventFactory(),
                this.bufferSize,
                AppendThreadFactory.create("append-disruptor-thread", false),
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        AppendHandler[] consumers = new AppendHandler[threadSize];
        for (int i = 0; i < threadSize; i++) {
            consumers[i] = new AppendHandler(filePath);
        }

        this.disruptor.handleEventsWithWorkerPool(consumers);
        this.disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        this.disruptor.start();
    }

    public void publishEvent(String line) {
        RingBuffer<AppendEvent> ringBuffer = this.disruptor.getRingBuffer();
        ringBuffer.publishEvent(new AppendEventTranslator(), line);
    }

    @Override
    public void destroy() throws Exception {
        this.disruptor.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--------------");
        this.start();
    }
}
