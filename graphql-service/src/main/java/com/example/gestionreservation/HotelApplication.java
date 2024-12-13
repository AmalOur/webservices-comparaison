package com.example.gestionreservation;

import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class HotelApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(HotelApplication.class, args);
		DefaultExports.initialize();
	}
}