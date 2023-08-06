package se.mohosman.blockgen.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import se.mohosman.blockgen.model.Block;

import java.util.Arrays;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "Block", groupId = "my-group-id")
    public void listen(Block block) {
        System.out.println("Received message: " + Arrays.toString(block.getHash()));
    }
}
