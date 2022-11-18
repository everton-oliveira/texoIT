package br.com.texo.teste.controller;

import br.com.texo.teste.entity.Movie;
import br.com.texo.teste.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public List<Movie> list(HttpServletResponse response) {

        try {
            List<Movie> list = movieService.list();

            if (list == null || list.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } else {
                response.setStatus(HttpStatus.OK.value());
            }

            return list;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return null;
    }

    @GetMapping("/title")
    public Movie movieByTitle(@RequestParam("filter") String filter, HttpServletResponse response) {

        try {
            Movie movie = movieService.findByTitle(filter);

            if (movie == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } else {
                response.setStatus(HttpStatus.OK.value());
            }

            return movie;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return null;
    }

    @GetMapping("/{id}")
    public Movie find(@PathVariable Long id, HttpServletResponse response) {

        try {
            Movie movie = movieService.find(id);

            if (movie == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } else {
                response.setStatus(HttpStatus.OK.value());
            }

            return movie;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return null;
    }

    @GetMapping("/winners")
    public List<Movie> winningMovies(HttpServletResponse response) {

        try {
            List<Movie> movies = movieService.winningMovies();

            if (movies == null || movies.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } else {
                response.setStatus(HttpStatus.OK.value());
            }

            return movies;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return null;
    }
}
