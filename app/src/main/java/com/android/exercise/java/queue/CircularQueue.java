package com.android.exercise.java.queue;

/**
 * CircularQueue
 * Created by laval on 2020/2/23.
 */
public class CircularQueue {
    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(3);
        print(queue.enQueue(1));  // 返回 true
        print(queue.enQueue(2));  // 返回 true
        print(queue.enQueue(3));  // 返回 true
        print(queue.enQueue(4));  // 返回 false，队列已满
        print(queue.Rear());  // 返回 3
        print(queue.isFull());  // 返回 true
        print(queue.deQueue());  // 返回 true
        print(queue.enQueue(4));  // 返回 true
        print(queue.Rear());  // 返回 4
    }

    private static void print(Object object) {
        System.out.println(object);
    }

    private Integer[] array;
    private int MAX_SIZE;
    private int start, end;

    /**
     * Initialize your data structure here. Set the size of the queue to be k.
     */
    public CircularQueue(int k) {
        MAX_SIZE = k;
        array = new Integer[MAX_SIZE];
        start = -1;
        end = -1;
    }

    /**
     * Insert an element into the circular queue. Return true if the operation is successful.
     */
    public boolean enQueue(int value) {
        if (!isFull()) {
            if (isEmpty()) {
                start = 0;
            }
            end = (end + 1) % MAX_SIZE;
            array[end] = value;
            return true;
        }
        return false;
    }

    /**
     * Delete an element from the circular queue. Return true if the operation is successful.
     */
    public boolean deQueue() {
        if (!isEmpty()) {
            if (start == end) {
                start = -1;
                end = -1;
                return true;
            }
            start = (start + 1) % MAX_SIZE;
            return true;
        }
        return false;
    }

    /**
     * Get the front item from the queue.
     */
    public int Front() {
        if (!isEmpty()) {
            return array[start];
        }
        return -1;
    }

    /**
     * Get the last item from the queue.
     */
    public int Rear() {
        if (!isEmpty()) {
            return array[end];
        }
        return -1;
    }

    /**
     * Checks whether the circular queue is empty or not.
     */
    public boolean isEmpty() {
        return start == -1;
    }

    /**
     * Checks whether the circular queue is full or not.
     */
    public boolean isFull() {
        return start == ((end + 1) % MAX_SIZE);
    }

}
