package com.chinacoal.microservcie.quickstart.provider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * <B>概要说明：</B> 广播交换机配置类 <BR>
 * @author Fly
 * @since 2020年4月17日
 */
@Configuration
public class FanoutRabbitConfig {

	public static String FANOUT_NAME = "test_fanout_queue";
	
	public static String FANOUT_EXCHANGE_NAME = "test_fanout_exchange";
	
	
	@Bean
	public Queue testFanoutQueue() {
		return new Queue(FANOUT_NAME,true);
	}
	
    @Bean
    public FanoutExchange testFanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME,true,false);
    }
    
    @Bean
    public Binding bindingFanout() {
        return BindingBuilder.bind(testFanoutQueue()).to(testFanoutExchange());
    }

}
