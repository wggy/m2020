package org.wangep.customtag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/***
 * created by wange on 2020/9/10 18:34
 */
public class UserNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("user", new UserDefinitionParser());
    }
}
