package com.android.playground.ui.activity.queue;


import androidx.annotation.NonNull;

public class Student implements Comparable {
    private int score;
    private String name;

    public Student(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "分数：" + score + "        姓名：" + name;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Student student = (Student) o;
        return student.getScore() - this.score;
    }
}
