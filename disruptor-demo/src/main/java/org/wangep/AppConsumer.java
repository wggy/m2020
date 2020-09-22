package org.wangep;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wangep.log.LogService;

import java.util.Map;
import java.util.UUID;

/***
 * created by wange on 2020/4/27 16:30
 */
public class AppConsumer {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");
        context.start();

        LogService logService = context.getBean("logService", LogService.class);
        for (int i = 0; i < 100 * 100; i++) {
            Map<String, Object> json = ImmutableMap.<String, Object>builder()
                    .put("line", i)
                    .put("value", UUID.randomUUID())
                    .build();

            logService.append(JSONObject.toJSONString(json).concat(System.lineSeparator()));
        }
    }


}
