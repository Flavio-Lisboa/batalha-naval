package com.batalhanaval;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Component
	public static class PacotePadraoService implements SmartInitializingSingleton {
		@Override
		public void afterSingletonsInstantiated() {
		}
	}
}
