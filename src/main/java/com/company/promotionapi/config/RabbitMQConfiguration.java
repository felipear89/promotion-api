package com.company.promotionapi.config;

import com.company.promotionapi.listener.CampaignMessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public final static String UPDATE_CAMPAIGN_MESSAGE_QUEUE = "UPDATE_CAMPAIGN_QUEUE";

    @Bean
    Queue queue() {
        return new Queue(UPDATE_CAMPAIGN_MESSAGE_QUEUE, false);
    }

    @Bean
    TopicExchange updateCampaignExchange() {
        return new TopicExchange("UPDATE_CAMPAIGN_QUEUE");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange updateCampaignExchange) {
        return BindingBuilder
                .bind(queue)
                .to(updateCampaignExchange).with(UPDATE_CAMPAIGN_MESSAGE_QUEUE);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(UPDATE_CAMPAIGN_MESSAGE_QUEUE);
        container.setMessageListener(listenerAdapter);

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(CampaignMessageListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
