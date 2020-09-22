package org.wangep.websocket.dubbo.encrypt;

import com.alibaba.fastjson.JSON;
import org.wangep.websocket.dubbo.model.Message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/***
 * created by @author: wangep on 2020/5/9 16:38
 */
public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message message) throws EncodeException {
        return JSON.toJSONString(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
