package edu.lanh.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import edu.lanh.shop.config.StorageProperties;
import edu.lanh.shop.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TechShopLanhApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechShopLanhApplication.class, args);
	}

	
	// cho phep khoi tao cac thu muc
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args->{
			storageService.init();
		});
	}
}
