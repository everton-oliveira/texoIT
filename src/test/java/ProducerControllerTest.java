import br.com.texo.teste.controller.ProducerController;
import br.com.texo.teste.entity.Producer;
import br.com.texo.teste.helper.MovieCSV;
import br.com.texo.teste.helper.MovieListCSVReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProducerControllerTest extends IntegrationTest {

    @Autowired
    private ProducerController producerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(producerController)
                .setConversionService(createFormattingConversionService())
                .build();
    }

    @Test
    public void winInterval() throws Exception {

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/producers/winners/interval");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .build()
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();


        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    public void listProducers() throws Exception {

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/producers/");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .build()
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();


        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    public void winningProducers() throws Exception {

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/producers/winners");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .build()
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();

        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    public void findproducer() throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("id", 1);

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/producers/{id}");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .buildAndExpand(param)
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();


        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    public void producerNotFound() throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("id", 9999);

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/producers/{id}");

        mockMvc.perform(get(uriBuilder
                        .buildAndExpand(param)
                        .toUri()))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void findMovieByTitle() throws Exception {

        MovieListCSVReader movieListCSVReader = new MovieListCSVReader();
        List<MovieCSV> movieList = movieListCSVReader.read("movielist.csv");

        for (MovieCSV movieCSV : movieList) {
            List<Producer> producers = Producer.producersFromMovieCSV(movieCSV);

            for (Producer producer : producers) {
                UriComponentsBuilder uriBuilder =
                        UriComponentsBuilder.fromUriString("/producers/name")
                                .queryParam("filter", producer.getName())
                                .encode();

                MvcResult result = mockMvc
                        .perform(get(uriBuilder
                                .build()
                                .toUri()))
                        .andExpect(status().isOk())
                        .andReturn();

                logger.info(result.getResponse().getContentAsString());
            }
        }
    }
}
