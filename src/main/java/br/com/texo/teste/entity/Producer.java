package br.com.texo.teste.entity;

import br.com.texo.teste.helper.MovieCSV;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "producer")
public class Producer {

    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<Producer> producersFromMovieCSV(MovieCSV movieCSV) {

        List<Producer> producers = new ArrayList<>();
        String[] andList = movieCSV.getProducers().split(" and ");
        for (String nameList : andList) {
            String[] names = nameList.split(",");
            for (String name : names) {
                Producer producer = new Producer();
                producer.setName(name.trim());
                producers.add(producer);
            }
        }

        return producers;
    }
}
