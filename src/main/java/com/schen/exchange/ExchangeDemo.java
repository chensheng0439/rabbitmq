package com.schen.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ExchangeDemo {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.10.8");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
       // channel.exchangeDeclare("exchange1","direct",true);
       // String queueName = channel.queueDeclare().getQueue();
       // System.out.println("queueName="+queueName);
       // channel.queueBind(queueName,"exchange1","11111111");

        //删除交换器
        channel.exchangeDelete("exchange1",true);
        channel.close();
        connection.close();
    }
}
