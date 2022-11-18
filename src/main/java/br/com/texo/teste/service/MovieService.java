package br.com.texo.teste.service;

import br.com.texo.teste.entity.Movie;
import br.com.texo.teste.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    public List<Movie> winningMovies() {
        return movieRepository.findMovieByWinner(true);
    }

    public List<Movie> list() {
        return movieRepository.findAll();
    }

    public Movie findByTitle(String title) {
        return movieRepository.findMovieByTitle(title);
    }

    public Movie find(Long id) {
        return movieRepository.findById(id).orElse(null);
    }
}
