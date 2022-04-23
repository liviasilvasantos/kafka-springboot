package br.unicamp.prefeitura.kafka.producer.producer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unicamp.prefeitura.kafka.producer.producer.CarProducer;

@RestController
@RequestMapping("/cars")
public class CarController {

	@Autowired
	private CarProducer carProducer;

	@PostMapping
	public ResponseEntity<CarDTO> create(@RequestBody CarDTO carDTO) {
		CarDTO car = CarDTO.builder().id(UUID.randomUUID().toString()).color(carDTO.getColor()).model(carDTO.getModel())
				.build();
		carProducer.send(car);
		return ResponseEntity.status(HttpStatus.CREATED).body(car);
	}
}