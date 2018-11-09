package com.android.exercise.java.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查找第一个不重复的字符
 * Created by wangzhen on 2018/11/9.
 */
public class FetchCharacter {
    public static void main(String[] args) {
        FetchCharacter fetchCharacter = new FetchCharacter();
        System.out.println(fetchCharacter.find("alibaba"));
        System.out.println(fetchCharacter.find("abcddfac"));
        System.out.println(fetchCharacter.find("12321"));
        System.out.println(fetchCharacter.find("aabbcc"));
    }

    private Character find(String s) {
        return find1(s);
    }

    /**
     * 使用有序HashMap
     *
     * @param s input
     * @return output
     */
    private Character find0(String s) {
        if (null == s || s.length() < 1) {
            throw new IllegalArgumentException("string should not be null or empty");
        }
        HashMap<Character, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer count;
            if (map.containsKey(c)) {
                count = map.get(c) + 1;
            } else {
                count = 1;
            }
            map.put(c, count);
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 使用List
     *
     * @param s input
     * @return output
     */
    private Character find1(String s) {
        if (null == s || s.length() < 1) {
            throw new IllegalArgumentException("string should not be null or empty");
        }
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (list.contains(c)) {
                list.remove((Character) c);
            } else {
                list.add(c);
            }
        }
        return list.isEmpty() ? null : list.get(0);
    }
}
