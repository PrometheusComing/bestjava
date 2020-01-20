package com.best.java.enumtest;

/**
 * 枚举测试
 *
 */
public enum  Closes {
    SMALL(0,"小号"),
    MEDIUM(1,"中号"),
    LARGE(2,"大号"),
    MOSTLARGE(3,"超大号");
    private int size;

    private String desc;

    Closes(int size,String desc) {
        this.size = size;
        this.desc = desc;
    }
    public int getSize() {
        return size;
    }

    public String getDesc() {
        return desc;
    }

    public static void main(String[] args) {
        System.out.println(Closes.SMALL.getSize());
        System.out.println(Closes.SMALL.getDesc());
    }
}
