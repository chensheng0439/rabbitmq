package com.schen.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {

    private static final String EXCHANGE_NAME = "exchange_demo";

    private static final String ROUTING_KEY = "routingkey_demo";

    private static final String QUEUE_NAME = "queue_demo";

    private static final String IP_ADDRESS = "192.168.10.8";

    private static final Integer PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        // 设置主机地址
       // factory.setHost(IP_ADDRESS);
        // 设置端口号
       // factory.setPort(PORT);
        // 设置用户名
       // factory.setUsername("root");
        // 设置密码
        // factory.setPassword("root");
        // 通过URI的方式创建连接工厂
        factory.setUri("amqp://root:root@192.168.10.8:5672");
        // 创建链接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // ConnectionFactory和Channel中都有该方法，但是不建议使用
        Boolean flag = channel.isOpen();


        // 申明一个交换器（如果mq已经存在一个相同名称的交换器，则直接返回这个交换器，否则创建成功后返回）
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        // 申明一个对列（如果mq已经存在一个相同名称的对列，则直接返回这个对列，否则创建成功后返回）
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        // 交换器与对列绑定
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        String message = "Hello World";
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.close();
        connection.close();
    }
}
