package org.wangep.listener.java;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;

/***
 * created by wange on 2020/3/30 16:11
 */
@Slf4j
public class JavaShopping {

    public static void main(String[] args) throws InterruptedException {
        String goodsName = "iPhone X";
        ShoppingEventResource shoppingEventResource = new ShoppingEventResource();
        shoppingEventResource.addListener(new MyShoppingListener());
        shoppingEventResource.doAction(Action.cart, goodsName);
        TimeUnit.SECONDS.sleep(1);
        shoppingEventResource.doAction(Action.order, goodsName);
        TimeUnit.SECONDS.sleep(1);
        shoppingEventResource.doAction(Action.pay, goodsName);
    }


    @Getter
    private static class CartEvent extends EventObject {

        private String goodsName;

        public CartEvent(Object source, String goodsName) {
            super(source);
            this.goodsName = goodsName;
        }
    }

    @Getter
    private static class OrderEvent extends EventObject {
        private String goodsName;

        public OrderEvent(Object source, String goodsName) {
            super(source);
            this.goodsName = goodsName;
        }
    }

    @Getter
    private static class PayEvent extends EventObject {
        private String goodsName;

        public PayEvent(Object source, String goodsName) {
            super(source);
            this.goodsName = goodsName;
        }
    }

    private interface ShoppingListener extends EventListener {
        void handleCart(CartEvent cartEvent);

        void handleOrder(OrderEvent orderEvent);

        void handlePay(PayEvent payEvent);
    }

    private static class ShoppingEventResource {
        private Action action;
        private List<ShoppingListener> list = Collections.synchronizedList(new LinkedList<>());

        public void addListener(ShoppingListener shoppingListener) {
            list.add(shoppingListener);
        }

        public void removeListenr(ShoppingListener shoppingListener) {
            if (list.contains(shoppingListener)) {
                list.remove(shoppingListener);
            }
        }

        public void notifyEvent(Action action, String goodsName) {
            for (ShoppingListener listener : list) {
                switch (action) {
                    case cart:
                        listener.handleCart(new CartEvent(this, goodsName));
                        break;
                    case order:
                        listener.handleOrder(new OrderEvent(this, goodsName));
                        break;
                    case pay:
                        listener.handlePay(new PayEvent(this, goodsName));
                        break;
                    default:
                        log.error("error event type: {}", action.name());

                }
            }
        }

        public void doAction(Action action, String goodsName) {
            if (action != null) {
                notifyEvent(action, goodsName);
            }
        }
    }

    public static class MyShoppingListener implements ShoppingListener {

        @Override
        public void handleCart(CartEvent cartEvent) {
            log.info("商品：{}加入购物车，请在30min内下单", cartEvent.getGoodsName());
        }

        @Override
        public void handleOrder(OrderEvent orderEvent) {
            log.info("商品：{}下单成功，请在30min内结算", orderEvent.getGoodsName());
        }

        @Override
        public void handlePay(PayEvent payEvent) {
            log.info("商品：{}结算成功，稍后将为您发货", payEvent.getGoodsName());
        }
    }

    private enum Action {
        cart, order, pay
    }

}
