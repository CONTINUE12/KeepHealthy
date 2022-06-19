package com.wlsup.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//回调接口
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     *
     * @param correlationData 包含了消息的ID和其他数据信息 这个需要在发送方创建，否则没有
     * @param ack 返回的一个交换机确认状态 true 为确认 false 为未确认
     * @param cause 未确认的一个原因，如果ack为true的话，此值为null
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack){
            log.info("消息发送成功，id 是{} ",id);

            //可以进行其他的操作
            //......

        }else{
            log.info("消息发送失败，原因 是{} id 为{}",cause,id);

            //可以进行其他的补偿操作：比如重新发送消息等......
            //......
        }
    }
}
