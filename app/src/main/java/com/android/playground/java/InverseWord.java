package com.android.playground.java;

import java.util.Objects;
import java.util.Stack;

/**
 * 单词反转
 * Created by wangzhen on 2017/10/28.
 */
public class InverseWord {
    public static void main(String[] args) {
        System.out.println("单词反转（系统方法）：" + new StringBuffer("android").reverse().toString());
        System.out.println("单词反转（循环）：" + inverseWord("android"));
        System.out.println("单词反转（栈）：" + inverseWordViaStack("android"));
        System.out.println("句子反转：" + inverseSentence("Android. love I"));
    }

    /**
     * 句子反转
     *
     * @param sentence
     * @return
     */
    private static String inverseSentence(String sentence) {
        if (Objects.equals(sentence, ""))
            throw new NullPointerException("sentence不能为空");
        int pos = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sentence.length(); i++) {
            char item = sentence.charAt(i);
            if (item == ' ') {
                pos = 0;
            }
            builder.insert(pos, item);
            if (item != ' ') {
                pos++;
            }
        }
        return builder.toString();
    }

    /**
     * 单词反转 via 栈
     *
     * @param word
     * @return
     */
    private static String inverseWordViaStack(String word) {
        if (Objects.equals(word, ""))
            throw new NullPointerException("word不能为空");
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < word.length(); i++) {
            stack.push(String.valueOf(word.charAt(i)));
        }
        StringBuilder builder = new StringBuilder();
        while (stack.size() > 0) {
            builder.append(stack.pop());
        }
        return builder.toString();
    }

    /**
     * 单词反转
     *
     * @param word
     * @return
     */
    private static String inverseWord(String word) {
        if (Objects.equals(word, ""))
            throw new NullPointerException("word不能为空");
        String[] arrayWord = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            arrayWord[i] = String.valueOf(word.charAt(word.length() - 1 - i));
        }
        StringBuilder builder = new StringBuilder();
        for (String item : arrayWord) {
            builder.append(item);
        }
        return builder.toString();
    }
}
