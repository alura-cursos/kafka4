package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerService<T> {
    void parse(ConsumerRecord<String, Message<String>> record);
    String getTopic();
    String getConsumerGroup();
}
