package fr.exalt.businessmicroservicecustomer.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class CustomerTopic {
    @Value("${kafka.topic.name}")
    private String topic;
    @Value("${topic.retention}")
    private String retention;
    @Bean
    public NewTopic newCustomerTopic(){
        return TopicBuilder.name(topic)
                .partitions(2)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, retention)
                .build();
    }
}
