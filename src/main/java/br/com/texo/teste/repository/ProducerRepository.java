package br.com.texo.teste.repository;

import br.com.texo.teste.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    @Query(nativeQuery = true, value = "select distinct p.* from producer p join movie_producer mp on mp.producer_id = p.id join movie m on mp.movie_id = m.id where m.winner")
    List<Producer> listWinningProducers();

    Producer findByName(String name);

}
