package com.chinacoal.microservcie.quickstart.provider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * <B>概要说明：</B> 直连交换机配置类 <BR>
 * @author Fly
 * @since 2020年4月17日
 */
@Configuration
public class DirectRabbitConfig {

	public static String DIRECT_NAME = "test_direct_queue";
	
	public static String DIRECT_EXCHANGE_NAME = "test_direct_exchange";
	
	public static String ROUTING_KEY = "test_direct_routing";
	
	
	@Bean
	public Queue testDirectQueue() {
		return new Queue(DIRECT_NAME,true);
	}
	
    @Bean
    public DirectExchange testDirectExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME,true,false);
    }
    
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with(ROUTING_KEY);
    }

}
