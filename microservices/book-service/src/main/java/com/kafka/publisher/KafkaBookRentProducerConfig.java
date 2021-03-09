package com.kafka.publisher;

import com.kafka.BookRentMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaBookRentProducerConfig {

    @Value("${kafka.bootstrapServers}")
    private String bootstrapServers;

    @Bean(name = "bookRentProducerFactory")
    public ProducerFactory<String, BookRentMessage> producerFactory() {
        Map<String, Object> configProps = producerFactoryConfig();

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "bookRentKafkaTemplate")
    public KafkaTemplate<String, BookRentMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    private Map<String, Object> producerFactoryConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return configProps;
    }

}
