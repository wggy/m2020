package org.wangep.stack;

/***
 * created by wange on 2020/9/16 15:32
 */
public class SeqStack<T> implements Stack<T> {


    private int top = -1;

    private int capacity = 4;

    private T[] container;

    public SeqStack() {
        this.container = (T[]) new Object[capacity];
    }

    public SeqStack(int size) {
        this.container = (T[]) new Object[size];
    }

    @Override
    public Boolean isEmpty() {
        return top == -1;
    }

    @Override
    public void push(T data) {
        if (this.top + 2 >= this.capacity) {
            int tempCapacity = this.capacity << 1;
            T[] temp = (T[]) new Object[tempCapacity];
            System.arraycopy(this.container, 0, temp, 0, this.capacity);
            this.capacity = tempCapacity;
            this.container = temp;
        }

        this.container[++top] = data;
    }

    @Override
    public T peek() {
        if (this.top == -1) {
            return null;
        }
        return this.container[this.top];
    }

    @Override
    public T pop() {
        if (this.top == -1) {
            return null;
        }
        T topData = this.container[this.top];
        this.container[this.top--] = null;
        return topData;
    }

    @Override
    public int size() {
        return this.top;
    }

    @Override
    public String toString() {
        if (this.top == -1) {
            return "SqlStack[Empty]";
        }
        StringBuilder append = new StringBuilder(100);
        for (int i = 0; i <= top; i++) {
            append.append(this.container[i]).append(",");
        }
        return append.toString();
    }

    public static void main(String[] args) {
//        Stack<Integer> stack = new SeqStack<>();
//        stack.push(1);
//        stack.push(2);
//        stack.push(3);
//        stack.push(4);
//        stack.push(5);
//        stack.push(6);
//        System.out.println(stack.toString());

        System.out.println(isValid("["));
    }

    public static boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] charArray = s.toCharArray();
        char[] stackArray = new char[charArray.length];

        int top = -1;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '(' || charArray[i] == '[' || charArray[i] == '{') {
                stackArray[++top] = charArray[i];
            } else if (charArray[i] == ')' || charArray[i] == ']' || charArray[i] == '}') {
                if (top < 0) {
                    return false;
                }
                char popItem = stackArray[top--];
                if (!((popItem == '(' && charArray[i] == ')')
                        || (popItem == '[' && charArray[i] == ']')
                        || (popItem == '{' && charArray[i] == '}'))) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (top == -1) {
            return true;
        }
        return false;
    }
}
