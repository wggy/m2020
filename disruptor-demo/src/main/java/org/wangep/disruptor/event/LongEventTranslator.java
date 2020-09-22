package org.wangep.disruptor.event;

import com.lmax.disruptor.EventTranslatorOneArg;

/***
 * created by wange on 2020/4/27 13:26
 */
public class LongEventTranslator implements EventTranslatorOneArg<LongEvent, Long> {
    @Override
    public void translateTo(LongEvent longEvent, long l, Long aLong) {
        longEvent.setNumber(aLong);
    }
}
