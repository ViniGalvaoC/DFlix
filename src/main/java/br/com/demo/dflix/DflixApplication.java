package br.com.demo.dflix;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.demo.dflix.model.DadosFilme;
import br.com.demo.dflix.model.DadosSerie;
import br.com.demo.dflix.model.DadosTemporadaSerie;
import br.com.demo.dflix.model.DadosEpisodioSerie;
import br.com.demo.dflix.service.ConsumoApi;
import br.com.demo.dflix.service.ConverteDados;

@SpringBootApplication
public class DflixApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DflixApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?i=tt3896198&apikey=9bf2bfa");
		ConverteDados conversor = new ConverteDados();
		DadosFilme dadosFilme = conversor.obterDados(json, DadosFilme.class);
		System.out.println(dadosFilme);
		json = consumoApi.obterDados("http://www.omdbapi.com/?t=the+flash&apikey=9bf2bfa");
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);	
		json = consumoApi.obterDados("http://www.omdbapi.com/?t=the+flash&season=1&episode=1&apikey=9bf2bfa");
		DadosEpisodioSerie dadosEpisodioSerie = conversor.obterDados(json, DadosEpisodioSerie.class);
		System.out.println(dadosEpisodioSerie);

		List<DadosTemporadaSerie> listaTemporadas = new ArrayList<>();
		for (int i = 1; i <= dadosSerie.qtdTemporadas(); i++) {
			json = consumoApi.obterDados("http://www.omdbapi.com/?t=the+flash&season="+i+"&apikey=9bf2bfa");
			DadosTemporadaSerie dadosTemporadaSerie = conversor.obterDados(json, DadosTemporadaSerie.class);
			listaTemporadas.add(dadosTemporadaSerie);
		}
		listaTemporadas.forEach(System.out::println );
	}

	
}
