package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieDetailDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Transactional(readOnly = true)
	public Page<MovieDTO> find(Long paramGenreId, Pageable pageable){
		Long genreId = (paramGenreId == 0L) ? null : paramGenreId;
		Page<Movie> page = repository.findMovieByGenre(genreId, pageable);
		return page.map(x -> new MovieDTO(x));
		
	}

	@Transactional(readOnly = true)
	public MovieDetailDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		return new MovieDetailDTO(entity, entity.getGenre());
	}
	
	@Transactional(readOnly = true)
	public List<ReviewDTO> findReviews(Long movieId){
		List<Review> entity = reviewRepository.findReviewByMovie(movieId);
		return entity.stream().map(x -> new ReviewDTO(x, x.getUser())).collect(Collectors.toList());
	}
}
