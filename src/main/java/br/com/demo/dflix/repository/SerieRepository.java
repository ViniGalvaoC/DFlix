package br.com.demo.dflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.demo.dflix.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long>{
}
