package br.com.socialMeli.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("br.com.socialMeli.api.repository")
public class RestApiSocialMeliApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiSocialMeliApplication.class, args);
	}

}
