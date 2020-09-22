package org.wangep.log;

import org.wangep.log.publisher.AppendPublisher;

/***
 * created by wange on 2020/4/27 16:21
 */
public class LogProvider implements LogService {

    private static AppendPublisher appendPublisher;

    @Override
    public void append(String line) {
        appendPublisher.publishEvent(line);
    }

    public static void initPublisher(AppendPublisher publisher) {
        if (appendPublisher == null) {
            appendPublisher = publisher;
        }
    }
}
