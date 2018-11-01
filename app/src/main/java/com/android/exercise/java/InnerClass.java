package com.android.exercise.java;

public class InnerClass {
    public static void main(String[] args) {
        Bean1 bean1 = new InnerClass().new Bean1();
        System.out.println("Bean1 -> " + bean1.i);

        Bean2 bean2 = new Bean2();
        System.out.println("bean2 -> " + bean2.j);

        Bean.Bean3 bean3 = new Bean().new Bean3();
        System.out.println("bean3 -> " + bean3.k);
    }

    class Bean1 {
        public int i = 1;
    }

    static class Bean2 {
        public int j = 2;
    }
}

class Bean {
    class Bean3 {
        public int k = 3;
    }
}
