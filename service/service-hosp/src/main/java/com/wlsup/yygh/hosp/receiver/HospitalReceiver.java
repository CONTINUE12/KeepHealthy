package com.wlsup.yygh.hosp.receiver;

import com.wlsup.rabbitmq.config.QueueConfig;
import com.wlsup.rabbitmq.config.TtlQueueConfig;
import com.wlsup.rabbitmq.service.ConfirmRabbitService;
import com.wlsup.yygh.hosp.service.ScheduleService;
import com.wlsup.yygh.model.hosp.Schedule;
import com.wlsup.yygh.vo.msm.MsmVo;
import com.wlsup.yygh.vo.order.OrderMqVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
public class HospitalReceiver {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ConfirmRabbitService confirmRabbitService;

    //订单消费者！
    @Transactional //开启事务
    @RabbitListener(queues = {QueueConfig.QUEUE_ORDER})
    public void receiver(OrderMqVo orderMqVo, Message message, Channel channel) throws IOException {
        if(null != orderMqVo.getAvailableNumber()) {
            try {
                //下单成功更新预约数
                Schedule schedule = scheduleService.getScheduleId(orderMqVo.getScheduleId());
                schedule.setReservedNumber(orderMqVo.getReservedNumber());
                schedule.setAvailableNumber(orderMqVo.getAvailableNumber());
                scheduleService.update(schedule);

                //预约成功发送预约成功的短信通知
                MsmVo msmVo = orderMqVo.getMsmVo();
                if(null != msmVo) {
                    //短信消息生成者！ 生成短信信息到消息队列
                    //开启发布确认模式的消息队列
                    confirmRabbitService.sendMessage(QueueConfig.EXCHANGE_DIRECT_MSM, QueueConfig.ROUTING_MSM_ITEM, msmVo);
                }

                //等待用户支付订单，十分钟之后检查是否支付，如果未支付就需要进行取消订单处理
                //作为生产者，向TTL队列发送支付消息
                Long orderId = 111L; // 模拟用户进行支付的消息
                confirmRabbitService.sendMessage(TtlQueueConfig.TTLEXCHANGE_DIRECT_ORDER, TtlQueueConfig.RoutingTTL, orderId);

                //开启消息的手动应答
                /**
                 * 没有异常就确认消息
                 * basicAck(long deliveryTag, boolean multiple)
                 * deliveryTag:当前消息在队列中的的索引;
                 * multiple:为true的话就是批量确认
                 */
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e){
                /**
                 * 有异常就拒收消息，开启重试机制
                 * basicNack(long deliveryTag, boolean multiple, boolean requeue)
                 * requeue:true为将消息重返当前消息队列,重新发送给消费者;
                 *         false将消息丢弃
                 */
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        } else {
            //取消预约更新预约数
            Schedule schedule = scheduleService.getScheduleId(orderMqVo.getScheduleId());
            int availableNumber = schedule.getAvailableNumber().intValue() + 1;
            schedule.setAvailableNumber(availableNumber);
            scheduleService.update(schedule);

            //取消预约可以做专门发送取消预约的短信通知，这里就不做了！

        }

    }

}
