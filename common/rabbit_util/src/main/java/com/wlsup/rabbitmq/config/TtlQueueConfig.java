package com.wlsup.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

//申明交换机和队列，并进行绑定
@Configuration
public class TtlQueueConfig {
    /**TTL交换机*/
    public static final String TTLEXCHANGE_DIRECT_ORDER="ttlexchange.direct.order";
    /**TTL队列*/
    public static final String TTLQUEUE_ORDER="ttlqueue.order";
    /**TTL的Routingkey*/
    public static final String RoutingTTL="ttl";

    /**死信交换机*/
    public static final String DEAD_LETTER_EXCHANGE = "dead.letter.exchange";
    /**死信队列*/
    public static final String DEAD_LETTER_QUEUE = "dead.letter.queue";
    /**私信队列的Routingkey*/
    public static final String RoutingDead="dead";


    /**声明TTLExchange  别名*/
    @Bean("TTLExchange")
    public DirectExchange xDirectExchange(){
        return new DirectExchange(TTLEXCHANGE_DIRECT_ORDER);
    }

    /**声明DeadExchange  别名*/
    @Bean("DeadExchange")
    public DirectExchange yDirectExchange(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    /**声明TTLqueue*/
    @Bean("TTLqueue")
    public Queue TTLqueue(){
        HashMap<String, Object> map = new HashMap<>(4);
        /**设置死信队列的交换机*/
        map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        /**设置死信队列的routingKey*/
        map.put("x-dead-letter-routing-key", "TTLtoDead");
        /**设置过期时间，实际生产环境中一般为10分钟或者30三分钟*/
        map.put("x-message-ttl", 10000);

        return QueueBuilder.durable(TTLQUEUE_ORDER).withArguments(map).build();
    }

    /**声明死信队列*/
    @Bean("Deadqueue")
    public Queue Deadqueue(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    /**队列和交换机绑定*/
    @Bean
    public Binding TTLqueueBindingTTLExchange(@Qualifier("TTLqueue") Queue TTLqueue,
                                  @Qualifier("TTLExchange") Exchange TTLExchange){
        return BindingBuilder.bind(TTLqueue).to(TTLExchange).with(RoutingTTL).noargs();
    }

    @Bean
    public Binding DeadqueueBindingDeadExchange(@Qualifier("Deadqueue") Queue Deadqueue,
                                  @Qualifier("DeadExchange") Exchange DeadExchange){
        return BindingBuilder.bind(Deadqueue).to(DeadExchange).with(RoutingDead).noargs();
    }
}
