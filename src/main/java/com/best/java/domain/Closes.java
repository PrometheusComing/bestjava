package com.best.java.domain;

public enum  Closes {
    SMALL(0),
    MEDIUM(1),
    LARGE(2),
    MOSTLARGE(3);
    private int size;
    Closes(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

}
