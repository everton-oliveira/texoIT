package br.com.texo.teste.service;

import br.com.texo.teste.entity.Movie;
import br.com.texo.teste.entity.Producer;
import br.com.texo.teste.entity.Studio;
import br.com.texo.teste.helper.MovieCSV;
import br.com.texo.teste.helper.MovieListCSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

@Service
public class LoadMovieListService {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private StudioService studioService;

    private Map<String, Producer> mapProducer = new HashMap<>();
    private Map<String, Studio> mapStudio = new HashMap<>();

    @PostConstruct
    @Transactional
    public void init() throws URISyntaxException, IOException {
        loadMovieListFromCSVFile("movielist.csv");
    }

    private void loadMovieListFromCSVFile(String csvFileName) throws IOException {

        MovieListCSVReader movieListCSVReader = new MovieListCSVReader();
        List<MovieCSV> csvData = movieListCSVReader.read(csvFileName);
        createMovies(csvData);
    }

    private void createMovies(List<MovieCSV> cb) {
        cb.forEach(movieCSV -> {
            List<Producer> producers = createProducers(movieCSV);
            List<Studio> studios = createStudios(movieCSV);

            Movie movie = Movie.fromMovieCSV(movieCSV);
            movie.setProducers(producers);
            movie.setStudios(studios);
            movieService.save(movie);

        });
    }

    private List<Producer> createProducers(MovieCSV movieCSV) {
        List<Producer> producers = Producer.producersFromMovieCSV(movieCSV);
        ListIterator<Producer> itr = producers.listIterator();
        while (itr.hasNext()) {
            Producer producer = itr.next();
            Producer producerByName = mapProducer.get(producer.getName());

            if (producerByName == null) {
                producerService.save(producer);
                mapProducer.put(producer.getName(), producer);
            } else {
                itr.remove();
                itr.add(producerByName);
            }
        }

        return producers;
    }

    private List<Studio> createStudios(MovieCSV movieCSV) {
        List<Studio> studios = Studio.studiosFromMovieCSV(movieCSV);
        ListIterator<Studio> itr = studios.listIterator();
        while (itr.hasNext()) {
            Studio studio = itr.next();
            Studio studioByName = mapStudio.get(studio.getName());

            if (studioByName == null) {
                studioService.save(studio);
                mapStudio.put(studio.getName(), studio);
            } else {
                itr.remove();
                itr.add(studioByName);
            }
        }

        return studios;
    }
}
