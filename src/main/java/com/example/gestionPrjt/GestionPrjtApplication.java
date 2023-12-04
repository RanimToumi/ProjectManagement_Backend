package com.example.gestionPrjt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GestionPrjtApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionPrjtApplication.class, args);
	}

	@Configuration

//la configuration pour gérer les requêtes CORS.
// CORS est un mécanisme de sécurité qui permet de contrôler les requêtes faites par des navigateurs web depuis un domaine (origine) vers un autre.
	public class WebConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://localhost:4200")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowedHeaders("*")
					.allowCredentials(true);
		}
	}
}
