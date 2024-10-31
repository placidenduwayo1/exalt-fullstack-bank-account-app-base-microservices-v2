package fr.exalt.businessmicroserviceoperation.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    @Value("${kafka.topic.name}")
    private String [] topics;
    @Value("${topic.retention}")
    private String retention;

    @Bean
    public NewTopic depotRetraitOpsTopic() {
        return TopicBuilder.name(topics[0])
                .partitions(1)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, retention)
                .build();
    }
    @Bean
    public NewTopic transferOpTopic(){
        return TopicBuilder.name(topics[1])
                .partitions(1)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, retention)
                .build();
    }
}
