package org.wangep;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.wangep.factorybean.CustomerFactoryBean;
import org.wangep.factorybean.DemoService;

@Configuration
@ComponentScan(basePackageClasses = App.class)
public class App {
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(App.class);
        System.out.println("容器启动完成");
        DemoService demoService = applicationContext.getBean(DemoService.class);
        System.out.println(demoService);
//        CustomerFactoryBean factoryBean = applicationContext.getBean(CustomerFactoryBean.class);
        Object customerFactoryBean = applicationContext.getBean("customerFactoryBean");
        CustomerFactoryBean rawBean = (CustomerFactoryBean) applicationContext.getBean("&customerFactoryBean");
        System.out.println(customerFactoryBean);
        System.out.println(rawBean);
    }
}
