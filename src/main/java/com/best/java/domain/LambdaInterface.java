package com.best.java.domain;

@FunctionalInterface
public interface LambdaInterface {
    default void act() {
        System.out.println("act");
    }
    int action(LambdaTest value);
}
