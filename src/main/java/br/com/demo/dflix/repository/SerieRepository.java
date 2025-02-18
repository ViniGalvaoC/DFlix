package br.com.demo.dflix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.demo.dflix.model.Episodio;
import br.com.demo.dflix.model.Serie;
import enums.Categoria;

public interface SerieRepository extends JpaRepository<Serie, Long>{
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    Optional<List<Serie>> findByGeneroEquals(Categoria genero);

    Optional<List<Serie>> findByAvaliacaoGreaterThanEqual(Double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);
}
