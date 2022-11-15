package br.com.texo.teste.service;

import br.com.texo.teste.helper.MovieCSV;
import br.com.texo.teste.entity.Movie;
import br.com.texo.teste.entity.Producer;
import br.com.texo.teste.entity.Studio;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

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

    private void loadMovieListFromCSVFile(String csvFileName) throws URISyntaxException, IOException {

        List<MovieCSV> csvData = readCSV(csvFileName);
        createMovies(csvData);
    }

    private List<MovieCSV> readCSV(String csvFileName) throws URISyntaxException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource(csvFileName).toURI());
        Reader reader = Files.newBufferedReader(path);

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();

        return new CsvToBeanBuilder<MovieCSV>(csvReader)
                .withType(MovieCSV.class)
                .build()
                .stream()
                .collect(Collectors.toList());
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
