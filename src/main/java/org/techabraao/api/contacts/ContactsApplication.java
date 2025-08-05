package org.techabraao.api.contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ContactsApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContactsApplication.class, args);
	}
}
