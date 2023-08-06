package se.mohosman.blockgen.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import se.mohosman.blockgen.model.Block;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
        @Bean
        public ProducerFactory<String, Block> producerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
            return new DefaultKafkaProducerFactory<>(configProps);
        }

    @Bean
    public KafkaTemplate<String, Block> userKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}



