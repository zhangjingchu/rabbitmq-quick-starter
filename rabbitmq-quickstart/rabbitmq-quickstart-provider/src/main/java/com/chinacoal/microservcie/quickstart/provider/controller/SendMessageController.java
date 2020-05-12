/**
 * Copyright 2020 TPRI. All Rights Reserved.
 */
package com.chinacoal.microservcie.quickstart.provider.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.chinacoal.microservcie.quickstart.provider.config.DirectRabbitConfig;
import com.chinacoal.microservcie.quickstart.provider.config.FanoutRabbitConfig;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author FLY
 * @since 2020年4月20日
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SendMessageController {

	@Value("${say-hi-format:privoder say hi to '%s'!}")
	private String sayHiFormat;
	
	@Autowired
	private  RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法
 
	
	
	@GetMapping("/direct/say_hi/{name}")
    public String sendDirectMessage(@PathVariable("name") String name) {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = String.format(sayHiFormat, name);
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
       
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        
		CorrelationData correlationData = new CorrelationData(DirectRabbitConfig.DIRECT_EXCHANGE_NAME + UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE_NAME, DirectRabbitConfig.ROUTING_KEY, JSON.toJSONString(map),correlationData);
        return "ok";
    }
    
	@GetMapping("/fanout/say_hi/{name}")
    public String sendFanoutMessage(@PathVariable("name") String name) {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = String.format(sayHiFormat, name);
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
       
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
		
        CorrelationData correlationData = new CorrelationData(FanoutRabbitConfig.FANOUT_EXCHANGE_NAME + UUID.randomUUID().toString());
        
    	rabbitTemplate.convertAndSend(FanoutRabbitConfig.FANOUT_EXCHANGE_NAME, null, JSON.toJSONString(map),correlationData);
        return "ok";
    }
}
