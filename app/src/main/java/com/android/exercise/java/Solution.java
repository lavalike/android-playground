package com.android.exercise.java;

/**
 * 给定一组字符，使用原地算法将其压缩。
 * 压缩后的长度必须始终小于或等于原数组长度。
 * 数组的每个元素应该是长度为1 的字符（不是 int 整数类型）。
 * 在完成原地修改输入数组后，返回数组的新长度。
 * Created by wangzhen on 2018/8/24.
 */
class Solution {

    public static void main(String[] args) {
//        char[] chars = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
//        char[] chars = {'a'};
//        char[] chars = {'a','b','b','b','b','b','b','b','b','b','b','b','b'};
//        char[] chars = {'a', 'b', 'c'};
        char[] chars = {'a','a','a','a','b','a'};
        System.out.println("chars 长度 " + compress(chars));
        System.out.println("chars " + printChars(chars));
    }

    public static int compress(char[] chars) {
        int count = 0;
        for (int i = 0; i < chars.length; ) {
            if (i >= chars.length - 1) {
                count++;
                chars[count - 1] = chars[i];
                break;
            }
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] != chars[j] || j >= chars.length - 1) {
                    if (j >= chars.length - 1 && chars[i] == chars[j]) {
                        j++;
                    }
                    if ((j - i) < 2) {
                        count++;
                        chars[count - 1] = chars[i];
                    } else if ((j - i) < 10) {
                        count += 2;
                        chars[count - 2] = chars[i];
                        chars[count - 1] = Character.forDigit(j - i, 10);
                    } else if ((j - i) < 100) {
                        count += 3;
                        chars[count - 3] = chars[i];
                        chars[count - 2] = Character.forDigit((j - i) / 10, 10);
                        chars[count - 1] = Character.forDigit((j - i) % 10, 10);
                    } else if ((j - i) < 1000) {
                        count += 4;
                        chars[count - 4] = chars[i];
                        chars[count - 3] = Character.forDigit((j - i) / 100, 10);
                        chars[count - 2] = Character.forDigit((j - i) / 10 % 10, 10);
                        chars[count - 1] = Character.forDigit((j - i) % 10, 10);
                    } else {
                        count += 5;
                        chars[count - 5] = chars[i];
                        chars[count - 4] = Character.forDigit((j - i) / 1000, 10);
                        chars[count - 3] = Character.forDigit((j - i) / 10 / 10 % 10, 10);
                        chars[count - 2] = Character.forDigit((j - i) / 10 % 10, 10);
                        chars[count - 1] = Character.forDigit((j - i) % 10, 10);
                    }
                    i = j;
                    break;
                }
            }
        }
        return count;
    }

    private static String printChars(char[] chars) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            buffer.append(chars[i]).append(",");
        }
        return buffer.toString();
    }
}