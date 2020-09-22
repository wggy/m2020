package org.wangep.log.factory;

import com.google.common.base.Joiner;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/***
 * created by wange on 2020/4/27 15:01
 */
public class AppendThreadFactory implements ThreadFactory {
    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("append");
    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1);
    private static final Joiner JOINER = Joiner.on("-");

    private final String namePrefix;
    private final boolean daemon;

    private AppendThreadFactory(String prefix, boolean daemon) {
        this.namePrefix = prefix;
        this.daemon = daemon;
    }

    public static ThreadFactory create(String prefix, boolean daemon) {
        return new AppendThreadFactory(prefix, daemon);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(THREAD_GROUP, r, JOINER.join(THREAD_GROUP.getName(), namePrefix, THREAD_NUMBER.getAndIncrement()));
        thread.setDaemon(daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
