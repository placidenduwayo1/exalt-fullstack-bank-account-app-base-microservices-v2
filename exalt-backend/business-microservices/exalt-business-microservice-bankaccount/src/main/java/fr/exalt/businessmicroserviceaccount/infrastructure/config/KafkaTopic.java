package fr.exalt.businessmicroserviceaccount.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    private static final int NB_REPLICAS = 1;
    @Value("${kafka.topic.name}")
    private String [] topics;
    @Value("${topic.retention}")
    private String retention;
    @Bean
    public NewTopic newTopicCurrentAccount(){
        return TopicBuilder.name(topics[0])
                .partitions(2)
                .replicas(NB_REPLICAS)
                .config(TopicConfig.RETENTION_MS_CONFIG,retention)
                .build();
    }
    @Bean
    public NewTopic newTopicSavingAccount(){
        return TopicBuilder.name(topics[1])
                .partitions(2)
                .replicas(NB_REPLICAS)
                .config(TopicConfig.RETENTION_MS_CONFIG, retention)
                .build();
    }
}
