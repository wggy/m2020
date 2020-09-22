package org.wangep.disruptor.event;

import com.lmax.disruptor.EventFactory;

/***
 * created by wange on 2020/4/27 13:25
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
