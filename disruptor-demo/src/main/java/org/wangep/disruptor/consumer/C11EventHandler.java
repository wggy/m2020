package org.wangep.disruptor.consumer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.wangep.disruptor.event.LongEvent;

/***
 * created by wange on 2020/4/27 13:27
 */
public class C11EventHandler implements EventHandler<LongEvent>, WorkHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long l, boolean b) throws Exception {
        long number = event.getNumber();
        number += 10;
        System.out.println(System.currentTimeMillis() + ": c1-1 consumer finished.number=" + number);
    }

    @Override
    public void onEvent(LongEvent event) throws Exception {
        long number = event.getNumber();
        number += 10;
        System.out.println(System.currentTimeMillis() + ": c1-1 consumer finished.number=" + number);
    }
}
