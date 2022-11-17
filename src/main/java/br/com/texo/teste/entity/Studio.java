package br.com.texo.teste.entity;

import br.com.texo.teste.helper.MovieCSV;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studio")
public class Studio implements Serializable {

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

    public static List<Studio> studiosFromMovieCSV(MovieCSV movieCSV) {

        List<Studio> studios = new ArrayList<>();
        String[] names = movieCSV.getStudios().split(",");
        for (String name : names) {
            Studio producer = new Studio();
            producer.setName(name.trim());
            studios.add(producer);
        }

        return studios;
    }
}
