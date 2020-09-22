package org.wangep.log.handler;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.lmax.disruptor.WorkHandler;
import org.wangep.log.event.AppendEvent;

import java.io.File;


/***
 * created by wange on 2020/4/27 14:52
 */
public class AppendHandler implements WorkHandler<AppendEvent> {

    private final String filePath;

    public AppendHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void onEvent(AppendEvent appendEvent) throws Exception {
        Files.append(appendEvent.getLine(), new File(filePath), Charsets.UTF_8);
    }
}
