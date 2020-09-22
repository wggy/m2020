package org.wangep.websocket.stomp.model;

import lombok.Getter;
import lombok.Setter;

/***
 * created by wange on 2020/5/8 10:53
 */
@Setter
@Getter
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
