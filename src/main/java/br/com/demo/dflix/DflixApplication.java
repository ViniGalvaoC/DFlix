package br.com.demo.dflix;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.demo.dflix.principal.Principal;
import br.com.demo.dflix.repository.FilmeRepository;
import br.com.demo.dflix.repository.SerieRepository;

@SpringBootApplication
public class DflixApplication implements CommandLineRunner{

	@Autowired
    private SerieRepository serieRepository;

	@Autowired
    private FilmeRepository filmeRepository;

	public static void main(String[] args) {
		SpringApplication.run(DflixApplication.class, args);
		
	}

	
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(serieRepository,filmeRepository);
		principal.exibeMenu();
	}
}
