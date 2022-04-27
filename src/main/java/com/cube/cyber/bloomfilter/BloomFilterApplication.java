package com.cube.cyber.bloomfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cube.cyber.bloomfilter.*"})
public class BloomFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloomFilterApplication.class, args);
	}
}
