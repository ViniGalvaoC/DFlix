package br.com.demo.dflix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.demo.dflix.model.Filme;
import enums.Categoria;

public interface FilmeRepository extends JpaRepository<Filme,Long>{
    Optional<Filme> findByTituloContainingIgnoreCase(String nomeFilme);

    Optional<List<Filme>> findByGeneroEquals(Categoria genero);

    Optional<List<Filme>> findByAvaliacaoGreaterThanEqual(Double avaliacao);
}
