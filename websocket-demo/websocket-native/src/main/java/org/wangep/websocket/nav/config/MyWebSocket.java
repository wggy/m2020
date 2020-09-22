package org.wangep.websocket.nav.config;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.wangep.websocket.nav.encrypt.MessageDecoder;
import org.wangep.websocket.nav.encrypt.MessageEncoder;
import org.wangep.websocket.nav.model.Message;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/***
 * created by @author: wangep on 2020/5/9 16:08
 */
@Log4j2
@Component
@ServerEndpoint(value = "/ws", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class MyWebSocket {
    private static AtomicLong onlineCnt = new AtomicLong(0L);
    public static List<MyWebSocket> webSockets = new CopyOnWriteArrayList<>();


    private Map<String, Session> sessionMap = Maps.newConcurrentMap();


    @OnOpen
    public void onOpen(Session session) {
        onlineCnt.incrementAndGet();
        webSockets.add(this);
        sessionMap.putIfAbsent(session.getId(), session);
    }

    @OnClose
    public void onClose() {
        onlineCnt.decrementAndGet();
        webSockets.remove(this);
    }

    @OnMessage
    public Message onMessage(Session session, String text) {
        String msg = String.format("Client[%s] message: %s", session.getId(), text);
        log.info("/ws Server received: {}", msg);
        new PushThread(session).start();
        return new Message(Message.MessageType.CHAT, "请求已提交，等着消息推送吧", session.getId());
    }

    private static class PushThread extends Thread {

        private Session session;

        public PushThread(Session session) {
            this.session = session;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                String dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                log.info("发送定时器时间戳：" + dateTime);
                Message message = new Message();
                message.setType(Message.MessageType.CHAT);
                message.setSender("SystemSched");
                message.setContent("第" + (i + 1) + "次推送，时间：" + dateTime);
                try {
                    session.getBasicRemote().sendText(JSONObject.toJSONString(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
