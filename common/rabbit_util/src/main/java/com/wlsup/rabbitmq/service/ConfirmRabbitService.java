package com.wlsup.rabbitmq.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class ConfirmRabbitService {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    MyCallBack myCallBack;

    /**该注解会在其他注解执行完毕之后，进行一个属性的注入，必须将该类注入到rabbitTemplate的内部类中
     * 内部类就是这个ConfirmCallback
     * */
    @PostConstruct
    public void init(){
        //设置回调接口
        rabbitTemplate.setConfirmCallback(myCallBack);
    }

    public boolean sendMessage(String exchange,String routingKey,Object message){
        //让消息绑定一个id值，全局唯一
        CorrelationData correlationData1 = new CorrelationData(UUID.randomUUID().toString());
        //发送消息
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData1);
        return true;
    }
}
