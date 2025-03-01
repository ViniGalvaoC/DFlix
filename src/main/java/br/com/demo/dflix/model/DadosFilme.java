package br.com.demo.dflix.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import enums.Categoria;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosFilme(@JsonAlias("Title")String titulo,
    @JsonAlias("Year") String anoLancamento,
    @JsonAlias("Runtime") String duracao,
    @JsonAlias("imdbRating") String avaliacao,
    @JsonAlias("Actors") String atores,
    @JsonAlias("Poster") String poster,
    @JsonAlias("Genre") String genero,
    @JsonAlias("Plot") String sinopse) {

}
