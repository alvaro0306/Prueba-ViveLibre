package com.vivelibre.api.simulada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {"com.vivelibre.api.simulada"})
@PropertySource("classpath:application.properties")
public class Main 
{
	public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
