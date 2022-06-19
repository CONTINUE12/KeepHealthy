package com.wlsup.yygh.order.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Redisson配置类
@Configuration
public class RedissonConfig {
    //返回Redisson客户端
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://60.205.125.6:6379");
        RedissonClient client = Redisson.create(config);
        return client;
    }
}
