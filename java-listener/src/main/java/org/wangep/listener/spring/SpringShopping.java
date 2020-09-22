package org.wangep.listener.spring;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/3/30 16:48
 */
@Slf4j
@Service
public class SpringShopping {

    @Autowired
    private SpringEventResource springEventResource;

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            springEventResource.doAction(Action.cart, "iPhone X");
            TimeUnit.SECONDS.sleep(1);
            springEventResource.doAction(Action.order, "iPhone X");
            TimeUnit.SECONDS.sleep(1);
            springEventResource.doAction(Action.pay, "iPhone X");
        };
    }


    @EventListener
    @Async //异步监听
    public void handleCart(SpringCartEvent cartEvent) {
        log.info("商品：{}加购物车，时间：{}，请在30min内下单", cartEvent.getGoodsName(), cartEvent.getTimestamp());
    }

    @EventListener
    @Async //异步监听
    public void handleOrder(SpringOrderEvent orderEvent) {
        log.info("商品：{}下单成功，时间：{}，请在30min内结算", orderEvent.getGoodsName(), orderEvent.getTimestamp());
    }

    @EventListener
    @Async //异步监听
    public void handlePay(SpringPayEvent payEvent) {
        log.info("商品：{}结算成功，时间：{}，稍后将为您发货", payEvent.getGoodsName(), payEvent.getTimestamp());
    }

    @Getter
    private static class SpringCartEvent extends ApplicationEvent {

        private String goodsName;

        public SpringCartEvent(Object source, String goodsName) {
            super(source);
            this.goodsName = goodsName;
        }
    }

    @Getter
    private static class SpringOrderEvent extends ApplicationEvent {

        private String goodsName;

        public SpringOrderEvent(Object source, String goodsName) {
            super(source);
            this.goodsName = goodsName;
        }
    }

    @Getter
    private static class SpringPayEvent extends ApplicationEvent {

        private String goodsName;

        public SpringPayEvent(Object source, String goodsName) {
            super(source);
            this.goodsName = goodsName;
        }
    }

    @Slf4j
    @Service
    private static class SpringEventResource implements ApplicationEventPublisherAware {

        private ApplicationEventPublisher applicationEventPublisher;

        @Override
        public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
            this.applicationEventPublisher = applicationEventPublisher;
        }

        public void doAction(Action action, String goodsName) {
            switch (action) {
                case cart:
                    this.applicationEventPublisher.publishEvent(new SpringCartEvent(this, goodsName));
                    break;
                case order:
                    this.applicationEventPublisher.publishEvent(new SpringOrderEvent(this, goodsName));
                    break;
                case pay:
                    this.applicationEventPublisher.publishEvent(new SpringPayEvent(this, goodsName));
                    break;
                default:
                    log.error("error event type: {}", action.name());
            }
        }
    }

    private enum Action {
        cart, order, pay
    }
}
