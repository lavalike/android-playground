package com.android.exercise.java.sort;

/**
 * 快速排序
 * 又称划分交换排序（partition-exchange sort），一种排序算法，最早由东尼·霍尔提出。在平均状况下，排序n个项目要Ο(n log n)次比较。在最坏状况下则需要Ο(n2)次比较，但这种状况并不常见。事实上，快速排序通常明显比其他Ο(n log n)算法更快，因为它的内部循环（inner loop）可以在大部分的架构上很有效率地被实现出来
 * Created by wangzhen on 2018/10/23.
 */
public class PartitionExchangeSort {

    public static void main(String[] args) {
//        int[] ints = {1, 6, 3, 6, 9, 0, 3, 4, 5, 8, 7};
        int[] ints = {3, 0, 5, 1, 4};
//        System.out.println("排序前 -> " + parseIntArray(ints));
        sort(ints, 0, ints.length - 1);
//        System.out.println("排序后 -> " + parseIntArray(ints));
    }

    private static String parseIntArray(int[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0)
                builder.append(",");
            builder.append(args[i]);
        }
        return builder.toString();
    }

    private static void sort(int[] args, int start, int end) {
        if (end - start > 1) {
            int mid;
            mid = dividerAndChange(args, start, end);
            //对左部分排序
            sort(args, start, mid);
            //对右部分排序
            sort(args, mid + 1, end);
        }
    }

    private static int dividerAndChange(int[] args, int start, int end) {
        int pivot = args[start];
        while (start < end) {
            while (start < end && args[end] >= pivot) {
                end--;
            }
            if (start < end) {
                swap(args, start, end);
                start++;
            }
            while (start < end && args[start] < pivot) {
                start++;
            }
            if (start < end) {
                swap(args, end, start);
                end--;
            }
        }
        System.out.println("排序中 " + pivot + " -> " + parseIntArray(args));
        return start;
    }

    private static void swap(int[] args, int start, int end) {
        int temp = args[start];
        args[start] = args[end];
        args[end] = temp;
    }
}
