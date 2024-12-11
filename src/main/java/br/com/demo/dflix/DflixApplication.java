package br.com.demo.dflix;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.demo.dflix.principal.Principal;

@SpringBootApplication
public class DflixApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DflixApplication.class, args);
		
	}

	
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
