package br.com.demo.dflix.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporadaSerie(@JsonAlias("Season") Integer numero, @JsonAlias("Episodes") List<DadosEpisodioSerie> episodios){
    
}
