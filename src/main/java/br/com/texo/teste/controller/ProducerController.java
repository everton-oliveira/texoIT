package br.com.texo.teste.controller;

import br.com.texo.teste.dto.WinIntervalDTO;
import br.com.texo.teste.entity.Movie;
import br.com.texo.teste.entity.Producer;
import br.com.texo.teste.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/producers")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("winners/interval")
    public WinIntervalDTO winInterval(HttpServletResponse response) {

        WinIntervalDTO winIntervalDTO = new WinIntervalDTO();
        try {
            winIntervalDTO.setMin(producerService.listProducerMinIntervalWin());
            winIntervalDTO.setMax(producerService.listProducerMaxIntervalWin());
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return winIntervalDTO;
    }

    @GetMapping("/")
    public List<Producer> list(HttpServletResponse response) {

        try {
            List<Producer> list = producerService.list();

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

    @GetMapping("/winners")
    public List<Producer> listWinners(HttpServletResponse response) {

        try {
            List<Producer> list = producerService.listWinningProducers();

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

    @GetMapping("/{id}")
    public Producer find(@PathVariable Long id, HttpServletResponse response) {

        try {
            Producer producer = producerService.find(id);

            if (producer == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } else {
                response.setStatus(HttpStatus.OK.value());
            }

            return producer;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return null;
    }

    @GetMapping("/name")
    public Producer producerByName(@RequestParam("filter") String filter, HttpServletResponse response) {

        try {
            Producer producer = producerService.findByName(filter);

            if (producer == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } else {
                response.setStatus(HttpStatus.OK.value());
            }

            return producer;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return null;
    }
}
