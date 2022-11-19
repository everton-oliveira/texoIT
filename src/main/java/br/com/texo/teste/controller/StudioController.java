package br.com.texo.teste.controller;

import br.com.texo.teste.entity.Producer;
import br.com.texo.teste.entity.Studio;
import br.com.texo.teste.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/studios")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @GetMapping("/")
    public List<Studio> list(HttpServletResponse response) {

        try {
            List<Studio> list = studioService.list();

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
    public List<Studio> listWinners(HttpServletResponse response) {

        try {
            List<Studio> list = studioService.listWinningStudios();

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
    public Studio find(@PathVariable Long id, HttpServletResponse response) {

        try {
            Studio studio = studioService.find(id);

            if (studio == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } else {
                response.setStatus(HttpStatus.OK.value());
            }

            return studio;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return null;
    }

    @GetMapping("/name")
    public Studio studioByName(@RequestParam("filter") String filter, HttpServletResponse response) {

        try {
            Studio studio = studioService.findByName(filter);

            if (studio == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } else {
                response.setStatus(HttpStatus.OK.value());
            }

            return studio;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return null;
    }
}
