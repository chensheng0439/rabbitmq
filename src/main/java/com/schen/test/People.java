package com.schen.test;

public class People {

    private Car car;

    public People(Car car) {
        this.car = car;
    }
    public void run(){
        System.out.println("汽车运行.......");
        car.run();
    }
}
