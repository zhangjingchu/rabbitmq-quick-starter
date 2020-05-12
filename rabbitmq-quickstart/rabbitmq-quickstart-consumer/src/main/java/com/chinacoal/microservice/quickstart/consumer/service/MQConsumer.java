/**
 * Copyright 2020 TPRI. All Rights Reserved.
 */
package com.chinacoal.microservice.quickstart.consumer.service;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

/**
 * <B>系统名称：MQ消费者</B><BR>
 * @author FLY
 * @since 2020年4月22日
 */
@Slf4j
@Component
public class MQConsumer {

    @RabbitListener(queues = "test_fanout_queue")
	public void receiveFanoutMessage(String receiveMessage, Message message, Channel channel) throws Exception {
    	log.info("接收到消息:[{}]", receiveMessage);
    	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
    
    @RabbitListener(queues = "test_direct_queue")
   	public void receiveDirectMessage(String receiveMessage, Message message, Channel channel) throws Exception {
       	log.info("接收到消息:[{}]", receiveMessage);
       	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
   	}
}
