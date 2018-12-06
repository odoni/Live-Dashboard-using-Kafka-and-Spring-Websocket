package com.springKafka.liveDashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

@Service
public class KafkaProducerService {

    @Value("${kafka.topic}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Integer> kafkaTemplate;

    public void sendMessage(Integer msg) {
        kafkaTemplate.send(topicName, msg);
    }

    @PostConstruct
    public void init() {
        new Thread(() -> {
            while (true) {
                sendMessage(KafkaProducerService.generateRandomInt());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static int generateRandomInt() {
        int min = 0;
        int max = 120;
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
