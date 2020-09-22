package org.wangep.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/***
 * created by @author: wangep on 2020/5/8 17:04
 */
@Component
public class CustomerFactoryBean implements FactoryBean<DemoService> {
    @Override
    public DemoService getObject() throws Exception {
        return new DemoService();
    }

    @Override
    public Class<?> getObjectType() {
        return DemoService.class;
    }
}
