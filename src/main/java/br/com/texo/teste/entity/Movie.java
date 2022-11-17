package br.com.texo.teste.entity;

import br.com.texo.teste.helper.MovieCSV;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie implements Serializable {

    private Long id;
    private Integer year;
    private String title;
    private Boolean winner;
    private List<Producer> producers;
    private List<Studio> studios;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "year_movie")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_producer",
            joinColumns = {@JoinColumn(name = "movie_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "producer_id", referencedColumnName = "id")})
    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    @ManyToMany
    @JoinTable(name = "movie_studio",
            joinColumns = {@JoinColumn(name = "movie_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "studio_id", referencedColumnName = "id")})
    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    @Override
    public String toString() {
        return title;
    }

    public static Movie fromMovieCSV(MovieCSV movieCSV) {
        Movie movie = new Movie();
        movie.setYear(movieCSV.getYear());
        movie.setTitle(movieCSV.getTitle());
        movie.setWinner(movieCSV.getWinner().equalsIgnoreCase("yes"));
        return movie;
    }
}
