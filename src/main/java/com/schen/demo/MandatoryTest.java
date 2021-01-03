package com.schen.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class MandatoryTest {

    private static final String EXCHANGE_NAME = "exchange_demo";

    private static final String ROUTING_KEY = "routingkey_demo";

    private static final String QUEUE_NAME = "queue_demo";

    public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:root@192.168.10.8:5672");
        // 创建链接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();

        // 申明一个交换器（如果mq已经存在一个相同名称的交换器，则直接返回这个交换器，否则创建成功后返回）
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        // 申明一个对列（如果mq已经存在一个相同名称的对列，则直接返回这个对列，否则创建成功后返回）
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        // 交换器与对列绑定
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        String message = "mandatory test.....";
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME,"",true, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("Basic.Return返回的结果是："+message);
            }
        });
        channel.close();
        connection.close();
    }
}
