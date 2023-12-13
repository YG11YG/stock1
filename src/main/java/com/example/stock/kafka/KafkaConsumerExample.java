package com.example.stock.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    public static void main(String[] args) {
        // Kafka 소비자 설정
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "여기에_당신의_Kafka_서버를_넣어주세요");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 카프카 컨슈머 생성
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        // 토픽 1 구독
        consumer.subscribe(Collections.singletonList("realtime_stock_data"));

        // 토픽 1에서 메시지 수신
        ConsumerRecords<String, String> records1 = consumer.poll(Duration.ofMillis(100));
        records1.forEach(record -> System.out.printf("토픽 1 - offset = %d, key = %s, value = %s%n",
                record.offset(), record.key(), record.value()));

        // 토픽 2 구독
        consumer.subscribe(Collections.singletonList("top_stock_increase"));

        // 토픽 2에서 메시지 수신
        ConsumerRecords<String, String> records2 = consumer.poll(Duration.ofMillis(100));
        records2.forEach(record -> System.out.printf("토픽 2 - offset = %d, key = %s, value = %s%n",
                record.offset(), record.key(), record.value()));

        // 컨슈머 종료
        consumer.close();
    }
}