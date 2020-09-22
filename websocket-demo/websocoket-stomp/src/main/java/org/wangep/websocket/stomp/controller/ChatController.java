package org.wangep.websocket.stomp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.wangep.websocket.stomp.model.ChatMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/***
 * created by wange on 2020/5/8 10:54
 */
@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }


    @MessageMapping("/chat.time")
    public ChatMessage time(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        chatMessage.setType(ChatMessage.MessageType.CHAT);
        chatMessage.setSender("TimeRtn");
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        chatMessage.setContent("时间：" + dateTime);
        return chatMessage;
    }
}
