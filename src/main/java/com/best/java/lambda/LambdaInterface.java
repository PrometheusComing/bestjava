package com.best.java.lambda;

@FunctionalInterface
public interface LambdaInterface<T> {
    default void act() {
        System.out.println("act");
    }
    int action(T t);
}
