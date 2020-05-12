package com.chinacoal.microservcie.quickstart.provider.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class RabbitConfirmCallback implements ConfirmCallback,ReturnCallback {  
  
	@Autowired
	@Qualifier("rabbitTemplate")
	private RabbitTemplate rabbitTemplate;
	

	
	@PostConstruct
	public void init() {
		rabbitTemplate.setConfirmCallback(this); 
		rabbitTemplate.setReturnCallback(this); 
	}
	
    @Override  
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {  
    	String id = correlationData.getId();
    	 if (!ack) {  
             log.info("签收失败:[{}],[{}]",id,cause.toString());  
         } 
    }
    
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		String correlationId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
		
		log.info(correlationId + " 发送失败");
		log.info("消息主体 message : " + message);
		log.info("描述：" + replyText);
		log.info("消息使用的交换器 exchange : " + exchange);
		 
 
	}

}  