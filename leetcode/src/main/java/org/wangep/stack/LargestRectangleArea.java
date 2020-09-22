package org.wangep.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/***
 * created by wange on 2020/9/17 10:43
 */
public class LargestRectangleArea {
    public static void main(String[] args) {
        int[] array = {2, 1, 2};
        System.out.println(ansner(array));
    }


    private static int oNStack(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        if (heights.length == 1) {
            return heights[0];
        }
        int size = heights.length;
        int[] stack = new int[size];
        int top = 0;
        int max = 0;

        int[] newHeights = new int[size + 2];
        newHeights[0] = 0;
        System.arraycopy(heights, 0, newHeights, 1, size);
        newHeights[size + 1] = 0;
        size += 2;
        heights = newHeights;
        stack[0] = heights[0];

        for (int i = 1; i < size; i++) {
            while (stack[top] > heights[i]) {
                int stackHeight = stack[top--];
                int currArea = i - stackHeight - 1;
                max = Math.max(max, currArea);
            }
            stack[++top] = heights[i];
        }

        return max;
    }


    private static int ansner(int[] heights) {
        int len = heights.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return heights[0];
        }

        int res = 0;
        Deque<Integer> stack = new ArrayDeque<>(len);
        for (int i = 0; i < len; i++) {
            // 这个 while 很关键，因为有可能不止一个柱形的最大宽度可以被计算出来
            while (!stack.isEmpty() && heights[i] < heights[stack.peekLast()]) {
                int curHeight = heights[stack.pollLast()];
                while (!stack.isEmpty() && heights[stack.peekLast()] == curHeight) {
                    stack.pollLast();
                }

                int curWidth;
                if (stack.isEmpty()) {
                    curWidth = i;
                } else {
                    curWidth = i - stack.peekLast() - 1;
                }

                // System.out.println("curIndex = " + curIndex + " " + curHeight * curWidth);
                res = Math.max(res, curHeight * curWidth);
            }
            stack.addLast(i);
        }

        while (!stack.isEmpty()) {
            int curHeight = heights[stack.pollLast()];
            while (!stack.isEmpty() && heights[stack.peekLast()] == curHeight) {
                stack.pollLast();
            }
            int curWidth;
            if (stack.isEmpty()) {
                curWidth = len;
            } else {
                curWidth = len - stack.peekLast() - 1;
            }
            res = Math.max(res, curHeight * curWidth);
        }
        return res;
    }

    private static int largestRectangleArea(int[] heights) {

        int size = heights.length;
        int area = 0;
        for (int i = 0; i < size; i++) {
            int left = i - 1;
            int right = i + 1;
            int val = heights[i];
            int width = 1;
            while (left >= 0) {
                if (val <= heights[left]) {
                    width++;
                } else {
                    break;
                }
                left--;
            }

            while (right < size) {
                if (val <= heights[right]) {
                    width++;
                } else {
                    break;
                }
                right++;
            }
            int newArea = val * width;
            if (area < newArea) {
                area = newArea;
            }
        }

        return area;
    }
}
