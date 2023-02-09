package kafka.tech.hour.KafkaTechHour;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@SpringBootApplication
@Component
public class KafkaTechHourApplication implements CommandLineRunner {

//	private KafkaProducerService kafkaProducerService;
	@Autowired
	private  ApplicationContext applicationCtx;

//	public KafkaTechHourApplication(KafkaProducerService kafkaProducerService) {
//		this.kafkaProducerService = kafkaProducerService;
//	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaTechHourApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		String[] allBeanNames = applicationCtx.getBeanDefinitionNames();
		for(String beanName : allBeanNames) {
			System.out.println(beanName);
		}
//		System.out.println("Enter word!");
//		while(true) {
//			Scanner scanner = new Scanner(System.in);
//			String line = scanner.nextLine();
//			var keyValuePair = line.split("\\s");
//			System.out.println(keyValuePair[0] + " " +keyValuePair[1]);
//			kafkaProducerService.sendEvent(keyValuePair[0], keyValuePair[1]);
//		}
	}
}
