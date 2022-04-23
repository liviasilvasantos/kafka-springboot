package br.unicamp.prefeitura.kafka.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import br.unicamp.prefeitura.kafka.consumer.dto.CarDTO;

@Configuration
public class KafkaConsumerConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Value(value = "${spring.kafka.group-id}")
	private String groupId;

	@Bean
	public ConsumerFactory<String, CarDTO> CarConsumerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
				new JsonDeserializer<>(CarDTO.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CarDTO> carKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, CarDTO> factory = new ConcurrentKafkaListenerContainerFactory<String, CarDTO>();
		factory.setConsumerFactory(CarConsumerFactory());
		return factory;
	}

}
