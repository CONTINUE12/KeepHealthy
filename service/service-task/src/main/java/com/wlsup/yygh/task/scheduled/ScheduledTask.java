package com.wlsup.yygh.task.scheduled;

import com.wlsup.rabbitmq.config.QueueConfig;
import com.wlsup.rabbitmq.service.ConfirmRabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private ConfirmRabbitService confirmRabbitService;


    /**cron表达式，设置执行间隔
     * 每天8点执行 提醒就诊： 0 0 8 * * ？  (注意第7位要删掉，不用，6位就可以了)
     *
     * 每隔2分钟执行：0 0/2 * * * ? :
     * 2021-09-07 19:10:00
     * 2021-09-07 19:12:00
     * 2021-09-07 19:14:00
     * 2021-09-07 19:16:00
     * 2021-09-07 19:18:00
     */
    @Scheduled(cron = "0 0/2 * * * ?") //但是为了方便测试，我们设置每隔2分钟执行一次！
    public void taskPatient() {
        System.out.println("1111");
        //执行的操作是往mq发送短信消息,生成短信信息
        confirmRabbitService.sendMessage(QueueConfig.EXCHANGE_DIRECT_TASK,QueueConfig.ROUTING_TASK_8,"111");

    }
}
