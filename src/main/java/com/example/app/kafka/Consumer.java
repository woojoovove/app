package com.example.app.kafka;

import com.example.app.domain.message.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @KafkaListener(topics = "topic", groupId = "group_1")
    public void listener(ConsumerRecord<String, MessageDTO> consumerRecord) {
        sendToUsers(consumerRecord.value());
    }

    @Async
    void sendToUsers(MessageDTO message) {
        for(String token : message.getToken()) {
            System.out.print("{\n\r \"token\": \"" + token + "\",\r\n \"text\": \"");
            System.out.println(message.getMessage() + "\" \r\n}");
        }
    }
}