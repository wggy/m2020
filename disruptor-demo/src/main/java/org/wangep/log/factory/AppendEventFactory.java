package org.wangep.log.factory;

import com.lmax.disruptor.EventFactory;
import org.wangep.log.event.AppendEvent;

/***
 * created by wange on 2020/4/27 14:46
 */
public class AppendEventFactory implements EventFactory<AppendEvent> {
    @Override
    public AppendEvent newInstance() {
        return new AppendEvent();
    }
}
