package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
	
	@Query("SELECT obj "
			+ "FROM Movie obj "
				+ "INNER JOIN obj.genre gen "
			+ "WHERE (:genreId IS NULL OR gen.id = :genreId) "
			+ "ORDER BY obj.title")
	Page<Movie> findMovieByGenre(Long genreId, Pageable page);
	

}
