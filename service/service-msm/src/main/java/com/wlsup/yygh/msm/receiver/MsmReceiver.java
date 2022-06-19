package com.wlsup.yygh.msm.receiver;

import com.wlsup.rabbitmq.constant.MqConst;
import com.wlsup.yygh.msm.service.MsmService;
import com.wlsup.yygh.msm.service.RLYService;
import com.wlsup.yygh.vo.msm.MsmVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsmReceiver {

    @Autowired
    private MsmService smsService;

    @Autowired
    private RLYService rlyService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = {MqConst.ROUTING_MSM_ITEM}
    ))
    public void send(MsmVo msmVo, Message message, Channel channel) {
//        smsService.send(msmVo);
        rlyService.send(msmVo);
    }
}
