package org.wangep.log.translator;

import com.lmax.disruptor.EventTranslatorOneArg;
import org.wangep.log.event.AppendEvent;

/***
 * created by wange on 2020/4/27 14:47
 */
public class AppendEventTranslator implements EventTranslatorOneArg<AppendEvent, String> {
    @Override
    public void translateTo(AppendEvent appendEvent, long l, String s) {
        appendEvent.setLine(s);
    }
}
