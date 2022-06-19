package com.wlsup.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//申明交换机和队列，并进行绑定
@Configuration
public class QueueConfig {
    //订单消息
    public static final String EXCHANGE_DIRECT_ORDER="exchange.direct.order";
    public static final String ROUTING_ORDER="order";
    public static final String QUEUE_ORDER="queue.order";

    //短信消息
    public static final String EXCHANGE_DIRECT_MSM="exchange.direct.msm";
    public static final String ROUTING_MSM_ITEM="msm.item";
    public static final String QUEUE_MSM_ITEM="queue.msm.item";

    //定时任务消息
    public static final String EXCHANGE_DIRECT_TASK = "exchange.direct.task";
    public static final String ROUTING_TASK_8 = "task.8";
    public static final String QUEUE_TASK_8 = "queue.task.8";

    //申明OrderExchange交换机，别名
    @Bean("OrderExchange")
    public DirectExchange xDirectExchange(){
        return new DirectExchange(EXCHANGE_DIRECT_ORDER);
    }

    //申明MsmExchange交换机，别名
    @Bean("MsmExchange")
    public DirectExchange yDirectExchange(){
        return new DirectExchange(EXCHANGE_DIRECT_MSM);
    }

    //申明TaskExchange交换机，别名
    @Bean("TaskExchange")
    public DirectExchange zDirectExchange(){
        return new DirectExchange(EXCHANGE_DIRECT_TASK);
    }

    //声明订单队列
    @Bean("Orderqueue")
    public Queue Orderqueue(){
        return QueueBuilder.durable(QUEUE_ORDER).build();
    }

    //声明短信队列
    @Bean("Msmqueue")
    public Queue Msmqueue(){
        return QueueBuilder.durable(QUEUE_MSM_ITEM).build();
    }

    //声明定时任务队列
    @Bean("Taskqueue")
    public Queue Taskqueue(){
        return QueueBuilder.durable(QUEUE_TASK_8).build();
    }

    //队列和交换机绑定1
    @Bean
    public Binding OrderqueueBindingOrderExchange(@Qualifier("Orderqueue") Queue Orderqueue,
                                              @Qualifier("OrderExchange") Exchange OrderExchange){
        return BindingBuilder.bind(Orderqueue).to(OrderExchange).with(ROUTING_ORDER).noargs();
    }

    //队列和交换机绑定2
    @Bean
    public Binding MsmqueueBindingMsmExchange(@Qualifier("Msmqueue") Queue Msmqueue,
                                                  @Qualifier("MsmExchange") Exchange MsmExchange){
        return BindingBuilder.bind(Msmqueue).to(MsmExchange).with(ROUTING_MSM_ITEM).noargs();
    }

    //队列和交换机绑定3
    @Bean
    public Binding TaskqueueBindingTaskExchange(@Qualifier("Taskqueue") Queue Taskqueue,
                                                  @Qualifier("TaskExchange") Exchange TaskExchange){
        return BindingBuilder.bind(Taskqueue).to(TaskExchange).with(ROUTING_TASK_8).noargs();
    }
}
