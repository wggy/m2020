package org.wangep.websocket.nav.encrypt;

import com.alibaba.fastjson.JSON;
import org.wangep.websocket.nav.model.Message;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/***
 * created by @author: wangep on 2020/5/9 16:39
 */
public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String s) {
        return JSON.parseObject(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return JSON.isValid(s);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
