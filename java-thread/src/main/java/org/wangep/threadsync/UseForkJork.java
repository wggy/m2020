package org.wangep.threadsync;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/***
 * created by wange on 2020/6/19 11:28
 */
public class UseForkJork {


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTaskTmp task = new CountTaskTmp(1, 10);

        int ret = forkJoinPool.invoke(task);
        System.out.println(ret);

    }

    private static final class CountTaskTmp extends RecursiveTask<Integer> {

        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        CountTaskTmp(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            boolean canCompute = (end - start) <= THRESHOLD;
            if (canCompute) {
                for (int i=start; i<=end; i++) {
                    sum += i;
                }
            } else {
                int mid = (start + end) / 2;
                CountTaskTmp leftTask = new CountTaskTmp(start, mid);
                CountTaskTmp rightTask = new CountTaskTmp(mid + 1, end);

                leftTask.fork();
                rightTask.fork();

                int leftRet = leftTask.join();
                int rightRet = rightTask.join();

                sum = leftRet + rightRet;
            }

            return sum;

        }
    }
}
