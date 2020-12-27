package com.schen.test;

public class TestMain {

    public static void main(String[] args) {
        People people = new People(new Car() {
            @Override
            public void run() {
                System.out.println("Car run。。。。。。");
            }
        });
        people.run();
    }




}
