package br.com.texo.teste.repository;

import br.com.texo.teste.entity.Producer;
import br.com.texo.teste.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    @Query(nativeQuery = true, value = "select distinct s.* from studio s join movie_studio ms on ms.studio_id = s.id join movie m on ms.movie_id = m.id where m.winner")
    List<Studio> listWinningStudios();

    Studio findByName(String name);
}
