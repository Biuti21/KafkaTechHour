package kafka.tech.hour.KafkaTechHour;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaStreamService {

    public KafkaStreamService() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "streamAppId");
        KafkaStreams kafkaStreams = new KafkaStreams(createStream(), properties);
        kafkaStreams.start();
    }

    public Topology createStream() {

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream("example.topic.one", Consumed.with(Serdes.String(), Serdes.String()))
                .peek((key, value) -> System.out.println("Kafka Stream here! Received Key: " + key + " Value: " + value))
                .filter((key, value) -> "Ala".equals(value))
                .mapValues(value -> value + " ma kota")
                .to("example.topic.two", Produced.with(Serdes.String(), Serdes.String()));

        return builder.build();
    }
}
