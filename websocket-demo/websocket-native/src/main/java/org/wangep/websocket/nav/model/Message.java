package org.wangep.websocket.nav.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/***
 * created by @author: wangep on 2020/5/9 16:16
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
