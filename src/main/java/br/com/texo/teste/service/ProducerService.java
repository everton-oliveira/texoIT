package br.com.texo.teste.service;

import br.com.texo.teste.entity.Movie;
import br.com.texo.teste.entity.Producer;
import br.com.texo.teste.helper.ProducerIntervalWin;
import br.com.texo.teste.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MovieService movieService;

    public void save(Producer producer) {
        producerRepository.save(producer);
    }

    public List<Producer> list() {
        return producerRepository.findAll();
    }

    public Producer find(Long id) {
        return producerRepository.findById(id).orElse(null);
    }

    public List<Producer> listWinningProducers() {
        return producerRepository.listWinningProducers();
    }

    public List<ProducerIntervalWin> listProducerMinIntervalWin() {

        Map<Producer, List<Movie>> producerWinner = getProducerWinnerMoreThanOnce();

        OptionalInt min = producerWinner.values().stream()
                .mapToInt(movieList -> {
                    List<Movie> list = movieList.stream().sorted(Comparator.comparing(Movie::getYear))
                            .collect(Collectors.toList());

                    return list.get(1).getYear() - list.get(0).getYear();
                }).min();

        if (min.isPresent())
            return getWinnerInInterval(producerWinner, min.getAsInt());

        return null;
    }

    public List<ProducerIntervalWin> listProducerMaxIntervalWin() {

        Map<Producer, List<Movie>> producerWinner = getProducerWinnerMoreThanOnce();

        OptionalInt max = producerWinner.values().stream()
                .mapToInt(movieList -> {
                    List<Movie> list = movieList.stream().sorted(Comparator.comparing(Movie::getYear).reversed())
                            .collect(Collectors.toList());

                    return list.get(0).getYear() - list.get(1).getYear();
                }).max();

        if (max.isPresent())
            return getWinnerInInterval(producerWinner, max.getAsInt());

        return null;
    }

    private Map<Producer, List<Movie>> getProducerWinnerMoreThanOnce() {
        List<Movie> movies = movieService.winningMovies();

        return movies.stream()
                .flatMap(m -> m.getProducers().stream().map(p -> new AbstractMap.SimpleEntry<>(p, m)))
                .collect(
                        Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .entrySet().stream()
                .filter(producerListEntry -> producerListEntry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<ProducerIntervalWin> getWinnerInInterval(Map<Producer, List<Movie>> producerWinner, int interval) {

        List<ProducerIntervalWin> result = new ArrayList<>();

        producerWinner.entrySet().forEach(entry ->

            entry.getValue().stream().reduce((movie1, movie2) -> {

                if (movie2.getYear() - movie1.getYear() == interval) {
                    ProducerIntervalWin producerIntervalWin = new ProducerIntervalWin(
                            entry.getKey().getName(), interval, movie1.getYear(), movie2.getYear());

                    result.add(producerIntervalWin);
                }

                return movie2;
            })
        );

        return result;
    }

    public Producer findByName(String name) {
        return producerRepository.findByName(name);
    }
}
