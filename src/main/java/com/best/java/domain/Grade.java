package com.best.java.domain;

import java.io.Serializable;

/**
 * 学生成绩实体类
 */
public class Grade implements Serializable,Comparable<Grade> {
    private String name;
    private String id;
    private String classname;
    private int age;
    private Closes size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Closes getSize() {
        return size;
    }

    public void setSize(Closes size) {
        this.size = size;
    }

    public int compareTo(Grade o) {
        if (this.age < o.age) {
            return -1;
        } else if (this.age == o.age) {
            return 0;
        } else {
            return 1;
        }
    }
    public class ReportAge {
        public void reportYourAge() {
            System.out.println("my age is : " + age);
        }
    }
    public ReportAge getReportAge () {
        return new ReportAge();
    }
    public void reportYourAge() {
        ReportAge reportAge = new ReportAge();
        reportAge.reportYourAge();
    }
}
