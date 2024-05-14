package com.example.app;

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
        String message = "message";
        testProducer.create(message);
    }
}
