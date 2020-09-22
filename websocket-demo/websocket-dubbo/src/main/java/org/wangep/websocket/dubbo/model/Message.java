package org.wangep.websocket.dubbo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * created by @author: wangep on 2020/5/12 10:00
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
