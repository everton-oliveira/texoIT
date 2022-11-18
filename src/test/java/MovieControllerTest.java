import br.com.texo.teste.controller.MovieController;
import br.com.texo.teste.helper.MovieCSV;
import br.com.texo.teste.helper.MovieListCSVReader;
import org.junit.jupiter.api.BeforeAll;
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

public class MovieControllerTest extends IntegrationTest {

    @Autowired
    private MovieController movieController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setConversionService(createFormattingConversionService())
                .build();
    }

    @Test
    public void listMovies() throws Exception {

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/movies/");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .build()
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();


        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    public void findMovie() throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("id", 1);

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/movies/{id}");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .buildAndExpand(param)
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();


        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    public void findMovieByTitle() throws Exception {

        MovieListCSVReader movieListCSVReader = new MovieListCSVReader();
        List<MovieCSV> movieList = movieListCSVReader.read("movielist.csv");

        for (MovieCSV movieCSV : movieList) {
            UriComponentsBuilder uriBuilder =
                    UriComponentsBuilder.fromUriString("/movies/title")
                            .queryParam("filter", movieCSV.getTitle())
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

    @Test
    public void movieNotFound() throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("id", 9999);

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/movies/{id}");

        mockMvc.perform(get(uriBuilder
                        .buildAndExpand(param)
                        .toUri()))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void winningMovies() throws Exception {

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/movies/winners");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .build()
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();


        logger.info(result.getResponse().getContentAsString());
    }
}
