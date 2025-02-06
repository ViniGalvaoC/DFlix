package br.com.demo.dflix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.demo.dflix.model.Serie;
import enums.Categoria;

public interface SerieRepository extends JpaRepository<Serie, Long>{
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    Optional<List<Serie>> findByGeneroEquals(Categoria genero);

    Optional<List<Serie>> findByAvaliacaoGreaterThanEqual(Double avaliacao);
}
