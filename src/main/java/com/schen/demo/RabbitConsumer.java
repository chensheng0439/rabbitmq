package com.schen.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitConsumer {

    private static final String QUEUE_NAME = "queue_demo";

    private static final String IP_ADDRESS = "192.168.10.8";

    private static final Integer PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        Address[] adresses = new Address[]{
                new Address(IP_ADDRESS,PORT)
        };
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection = factory.newConnection(adresses);
        final Channel channel = connection.createChannel();
        channel.basicQos(64);
        /*Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv message "+ new String(body));
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                super.handleDelivery(consumerTag, envelope, properties, body);
            }
        };
        channel.basicConsume(QUEUE_NAME,consumer);*/

        channel.basicConsume(QUEUE_NAME, false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(Thread.currentThread().getName() + ":" + new String(body));
                String routingKey =  envelope.getRoutingKey();
                String contentType = properties.getContentType();
                long deliveryTag = envelope.getDeliveryTag();
                channel.basicAck(deliveryTag,true);
            }
        });
        // 等待回调函数执行完毕之后，关闭资源
        channel.close();
        connection.close();
    }
}
