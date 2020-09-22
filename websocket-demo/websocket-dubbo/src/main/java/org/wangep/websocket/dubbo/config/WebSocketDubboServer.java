package org.wangep.websocket.dubbo.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.wangep.websocket.dubbo.encrypt.MessageDecoder;
import org.wangep.websocket.dubbo.encrypt.MessageEncoder;
import org.wangep.websocket.dubbo.model.Message;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;




/***
 * created by @author: wangep on 2020/5/12 9:54
 */
@Log4j2
@Component
@ServerEndpoint(value = "/ws", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class WebSocketDubboServer {

    @OnOpen
    public void onOpen() {

    }



    @OnMessage
    public Message onMessage(Session session, String text) {
        String msg = String.format("Client[%s] message: %s", session.getId(), text);
        log.info("/ws Server received: {}", msg);
        return new Message();
    }



    @OnClose
    public void onClose() {

    }

}
