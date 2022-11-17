package br.com.texo.teste.service;

import br.com.texo.teste.entity.Studio;
import br.com.texo.teste.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioService {

    @Autowired
    private StudioRepository studioRepository;

    public void save(Studio studio) {
        studioRepository.save(studio);
    }

    public List<Studio> list() {
        return studioRepository.findAll();
    }
}
