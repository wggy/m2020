package org.wangep.disruptor.consumer;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.wangep.disruptor.event.LongEvent;
import org.wangep.disruptor.event.LongEventFactory;
import org.wangep.disruptor.event.LongEventTranslator;

import java.util.concurrent.Executors;

/***
 * created by wange on 2020/4/27 13:32
 */
public class Main {

    public static void main(String[] args) {
        int bufferSize = 1024 * 1024;

        EventFactory<LongEvent> eventFactory = new LongEventFactory();

        /**
         * 定义disruptor，基于单生产者阻塞策略
         */
        Disruptor<LongEvent> disruptor = new Disruptor<>(
                eventFactory,
                bufferSize,
                Executors.newCachedThreadPool(),
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        serialWithPool(disruptor);

        ringBuffer.publishEvent(new LongEventTranslator(), 10L);
        ringBuffer.publishEvent(new LongEventTranslator(), 100L);
    }

    /**
     * 并行处理策略
     *     | ---> ...
     *  p  | ---> c11
     *     | ---> c21
     * @param disruptor
     */
    private static void parallel(Disruptor<LongEvent> disruptor) {
        disruptor.handleEventsWith(new C11EventHandler(), new C21EventHandler());
        disruptor.start();
    }

    /***
     * 串行处理策略
     *
     * p ---> c11 ---> c21
     * @param disruptor
     */
    private static void serial(Disruptor<LongEvent> disruptor) {
        disruptor.handleEventsWith(new C11EventHandler()).then(new C21EventHandler());
        disruptor.start();
    }

    /***
     * 菱形处理策略
     *       | ---> c11 |
     * p --->|          | ---> c21
     *       | ---> c12 |
     * @param disruptor
     */
    private static void diamond(Disruptor<LongEvent> disruptor) {
        disruptor.handleEventsWith(new C11EventHandler(), new C12EventHandler()).then(new C21EventHandler());
        disruptor.start();
    }

    /***
     * 链式调用策略
     *        | ---> c11 ---> c12
     * p ---> |
     *        | ---> c21 ---> c22
     * @param disruptor
     */
    private static void chain(Disruptor<LongEvent> disruptor) {
        disruptor.handleEventsWith(new C11EventHandler()).then(new C12EventHandler());
        disruptor.handleEventsWith(new C21EventHandler()).then(new C22EventHandler());
        disruptor.start();
    }

    private static void parallelWithPool(Disruptor<LongEvent> disruptor) {
        disruptor.handleEventsWithWorkerPool(new C11EventHandler(), new C11EventHandler());
        disruptor.handleEventsWithWorkerPool(new C21EventHandler(), new C21EventHandler());
        disruptor.start();
    }

    private static void serialWithPool(Disruptor<LongEvent> disruptor) {
        disruptor.handleEventsWithWorkerPool(new C11EventHandler(), new C11EventHandler()).then(new C21EventHandler(), new C21EventHandler());
        disruptor.start();
    }
}
