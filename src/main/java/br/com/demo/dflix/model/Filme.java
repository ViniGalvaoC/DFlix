package br.com.demo.dflix.model;

import java.util.OptionalDouble;

import com.fasterxml.jackson.annotation.JsonAlias;

import enums.Categoria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String anoLancamento;

    private String duracao;

    private Double avaliacao;

    @Enumerated(EnumType.STRING)
    private Categoria genero;

    private String sinopse;

    private String poster;

    private String atores;

    public Filme(){};
    
    public Filme(DadosFilme dadosFilme){
        this.titulo = dadosFilme.titulo();
        this.anoLancamento = dadosFilme.anoLancamento();
        this.duracao = dadosFilme.duracao();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosFilme.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosFilme.genero().split(",")[0].trim());
        this.atores = dadosFilme.atores();
        this.poster = dadosFilme.poster();
        this.sinopse = dadosFilme.sinopse();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    @Override
    public String toString() {
        return String.format(
            """
                Gênero: %s
                Título: %s
                Ano de lançamento: %s
                Avaliação: %s
                Atores: %s
                Poster: %s
                Sinopse: %s
                Duracao: %s
            """
            ,genero,titulo,anoLancamento,avaliacao,atores,poster,sinopse,duracao);
    }
}
