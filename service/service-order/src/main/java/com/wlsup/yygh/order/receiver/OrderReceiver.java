package com.wlsup.yygh.order.receiver;

import com.wlsup.rabbitmq.config.QueueConfig;
import com.wlsup.rabbitmq.config.TtlQueueConfig;
import com.wlsup.yygh.order.service.OrderService;
import com.wlsup.yygh.order.service.WeixinService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OrderReceiver {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeixinService weixinService;

    // 监听定时任务的消息 即我是定时任务消费者
    @RabbitListener(queues = {QueueConfig.QUEUE_TASK_8})
    public void patientTips(Message message, Channel channel) throws IOException {
        //监听到消息后，执行patientTips();
        orderService.patientTips();
    }

    // 监听死信队列，判断用户是否完成订单支付
    @RabbitListener(queues = {TtlQueueConfig.DEAD_LETTER_QUEUE})
    public void payOrder(Message message, Channel channel) throws IOException {
        try {
            //监听到消息后，执行queryPayStatus()方法查询订单的支付状态
            //从message中获取订单id，这里简单模拟一下
            Long orderId = 111L;
            //查询订单的状态
            Map<String, String> payStatusMap = weixinService.queryPayStatus(orderId);
            //判断是否支付
//            boolean ispay = payStatusMap.get(ispay);
//            if (!isPay) {
//                //如果没支付，取消订单
//                orderService.cancelOrder(orderId);
//            }

            //消息手动应答
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            /**
             * 有异常就拒收消息，开启重试机制
             * basicNack(long deliveryTag, boolean multiple, boolean requeue)
             * requeue:true为将消息重返当前消息队列,重新发送给消费者;
             *         false将消息丢弃
             */
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
