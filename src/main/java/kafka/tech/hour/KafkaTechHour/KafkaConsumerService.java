package kafka.tech.hour.KafkaTechHour;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Component
@EnableScheduling
//@ConditionalOnProperty(value="kafka.client-role", havingValue = "consumer")
public class KafkaConsumerService {

    private final KafkaConsumer<String, String> consumer;

    public KafkaConsumerService() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "java-consumer-group-1");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of("example.topic.two"));
    }

    @Scheduled(fixedRate = 1000)
    public void consume() {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for(ConsumerRecord<String, String> record : records) {
            System.out.print("Consumer here! ");
            System.out.println("Partition: " + record.partition() + ", Offset: " + record.offset() +
                    ", Key: " + record.key() + ", Value: " + record.value());
        }
    }
}
