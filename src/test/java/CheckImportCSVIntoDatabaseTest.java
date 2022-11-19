import br.com.texo.teste.controller.MovieController;
import br.com.texo.teste.controller.ProducerController;
import br.com.texo.teste.controller.StudioController;
import br.com.texo.teste.entity.Producer;
import br.com.texo.teste.entity.Studio;
import br.com.texo.teste.helper.MovieCSV;
import br.com.texo.teste.helper.MovieListCSVReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CheckImportCSVIntoDatabaseTest extends IntegrationTest {

    @Autowired
    private MovieController movieController;

    @Autowired
    private ProducerController producerController;

    @Autowired
    private StudioController studioController;

    private MockMvc mockMvc;
    private List<MovieCSV> movieList;

    @BeforeEach
    public void init() throws IOException {
        mockMvc = MockMvcBuilders
                .standaloneSetup(movieController, producerController, studioController)
                .setConversionService(createFormattingConversionService())
                .build();

        MovieListCSVReader movieListCSVReader = new MovieListCSVReader();
        movieList = movieListCSVReader.read("movielist.csv");
    }

    @Test
    public void checkMoviesInDatabase() throws Exception {

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
    public void checkProducersInDatabase() throws Exception {

        List<String> names = movieList.stream().map(Producer::producersFromMovieCSV).collect(Collectors.toList()).stream()
                .flatMap(Collection::stream).map(Producer::getName).distinct().collect(Collectors.toList());

        for (String name : names) {

            UriComponentsBuilder uriBuilder =
                    UriComponentsBuilder.fromUriString("/producers/name")
                            .queryParam("filter", name)
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
    public void checkStudiosInDatabase() throws Exception {

        List<String> names = movieList.stream().map(Studio::studiosFromMovieCSV).collect(Collectors.toList()).stream()
                .flatMap(Collection::stream).map(Studio::getName).distinct().collect(Collectors.toList());

        for (String name : names) {
            UriComponentsBuilder uriBuilder =
                    UriComponentsBuilder.fromUriString("/studios/name")
                            .queryParam("filter", name)
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
