package org.wangep.threadsync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/***
 * created by wange on 2020/4/9 17:40
 */
public class UseExchanger {

    public static void main(String[] args) {
        List<String> buffer1 = new ArrayList<>();
        List<String> buffer2 = new ArrayList<>();

        Exchanger<List<String>> exchanger = new Exchanger<>();

        Producer producer = new Producer(buffer1, exchanger);
        Consumer consumer = new Consumer(buffer2, exchanger);

        Thread threadProducer = new Thread(producer);
        Thread threadConsumer = new Thread(consumer);
        threadProducer.start();
        threadConsumer.start();
    }

    static class Producer implements Runnable {
        // 1. 声明 List<String>对象，名为 buffer。这是等等要被相互交换的数据类型
        private List<String> buffer;

        // 2. 声明 Exchanger<List<String>>; 对象，名为exchanger。这个 exchanger 对象是用来同步producer和consumer的。
        private final Exchanger<List<String>> exchanger;


        // 3. 实现类的构造函数，初始化这2个属性。
        public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
            this.buffer = buffer;
            this.exchanger = exchanger;

        }

        // 4. 实现 run() 方法. 在方法内，实现10次交换。
        @Override
        public void run() {
            int cycle = 1;
            for (int i = 0; i < 10; i++) {
                System.out.printf("Producer: Cycle %d\n", cycle);
                // 5. 在每次循环中，加10个字符串到buffer。
                for (int j = 0; j < 10; j++) {
                    String message = "Event " + ((i * 10) + j);
                    System.out.printf("    Producer: %s\n", message);
                    buffer.add(message);
                }

                // 6. 调用 exchange() 方法来与consumer交换数据。此方法可能会抛出InterruptedException 异常, 加上处理代码。
                try {
                    buffer = exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Producer: " + buffer.size());
                cycle++;

            }
        }
    }

    static class Consumer implements Runnable {

        private List<String> buffer;
        private final Exchanger<List<String>> exchanger;

        public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
            this.buffer = buffer;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            int cycle = 1;
            for (int i = 0; i < 10; i++) {

                System.out.printf("Consumer: Cycle %d\n", cycle);
                try {
                    buffer = exchanger.exchange(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
                System.out.println("Consumer: " + buffer.size());
                // 14. 把producer发来的在buffer里的10字符串写到操控台并从buffer内删除，留空。System.out.println("Consumer: " + buffer.size());
                for (int j = 0; j < 10; j++) {
                    String message = buffer.get(0);
                    System.out.println("    Consumer: " + message);
                    buffer.remove(0);
                }
                cycle++;

            }
        }
    }
}
