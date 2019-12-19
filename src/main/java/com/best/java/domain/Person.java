package com.best.java.domain;

public class Person extends Animal {
    private String unit;
    public String getUnit() {
        return unit;
    }
    public void setUnitName(String unit,String name) {
        super.setName(name);
        this.unit = unit;
    }
    public Person(String name) {
        super(name);
    }
}
