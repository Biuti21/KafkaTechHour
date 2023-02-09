package kafka.tech.hour.KafkaTechHour;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

@Component
//@ConditionalOnProperty(value="kafka.client-role", havingValue = "producer")
@EnableScheduling
public class KafkaProducerService {

    private Queue<String> messageFeed = new LinkedList<>();

    private final KafkaProducer<String, String> producer;

    public KafkaProducerService() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("acks", "1");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(properties);
        initFeed();
    }

    private void initFeed() {
        messageFeed = new LinkedList<>();
        messageFeed.addAll(List.of("Ala", "Tomek", "Ewa", "Rysiek", "Kamil", "Ola"));
    }
    @Scheduled(fixedDelay = 1500, initialDelay = 2000)
    public void scheduledSend() throws ExecutionException, InterruptedException {
       var name = messageFeed.poll();

       sendEvent(null, name);
       messageFeed.add(name);
    }

    void sendEvent(String key, String value) throws ExecutionException, InterruptedException {
        var promisedResponse = producer.send(new ProducerRecord<>("example.topic.one", key, value));
        var recordMetaData = promisedResponse.get();
        System.out.println("Producer here! Sent message to Partition: " + recordMetaData.partition() +
                " with Offset: " + recordMetaData.offset() +
                " Key: " + key +
                " Value: " + value);
    }
}
