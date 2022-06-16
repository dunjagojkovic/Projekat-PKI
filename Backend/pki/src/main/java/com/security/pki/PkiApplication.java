package com.security.pki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

@RestController
@EnableJpaRepositories("repository")
@EntityScan("model")
@ComponentScan({"repository"})
@ComponentScan({"controller"})
@ComponentScan({"service"})
@ComponentScan({"security"})
@SpringBootApplication
public class PkiApplication {
	
	
	public static void main(String[] args) {

		Security.addProvider(new BouncyCastleProvider());

		SpringApplication.run(PkiApplication.class, args);
	}
	

	
}
