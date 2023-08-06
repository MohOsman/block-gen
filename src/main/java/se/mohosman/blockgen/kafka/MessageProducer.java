package se.mohosman.blockgen.kafka;

import com.google.gson.Gson;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import se.mohosman.blockgen.model.Block;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, Block> kafkaTemplate;



    public void sendMessage(String topic, Block block) {
        kafkaTemplate.send(topic, block);
    }
}
