package org.wangep.customtag;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/***
 * created by wange on 2020/9/10 18:37
 */
public class CustomTagTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        User user = (User) context.getBean("user");
        System.out.println(user.getUserName() + "----" + user.getEmail());
    }
}
