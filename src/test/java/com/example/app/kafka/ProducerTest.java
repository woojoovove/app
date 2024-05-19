package com.example.app.kafka;

import com.example.app.kafka.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProducerTest {

    @Autowired
    private Producer testProducer;

    @Test
    void test() {
        String message = "{\"token\":[\"token9\",\"token6\",\"token5\",\"token7\",\"token2\"],\"message\":\"[CRITICAL] string\"}";
        testProducer.create(message);
    }
}
