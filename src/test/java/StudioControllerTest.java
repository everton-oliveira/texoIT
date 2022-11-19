import br.com.texo.teste.controller.StudioController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudioControllerTest extends IntegrationTest {

    @Autowired
    private StudioController studioController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(studioController)
                .setConversionService(createFormattingConversionService())
                .build();
    }

    @Test
    public void listStudios() throws Exception {

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/studios/");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .build()
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();


        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    public void winningStudios() throws Exception {

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/studios/winners");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .build()
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();

        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    public void findStudio() throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("id", 1);

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/studios/{id}");

        MvcResult result = mockMvc
                .perform(get(uriBuilder
                        .buildAndExpand(param)
                        .toUri()))
                .andExpect(status().isOk())
                .andReturn();


        logger.info(result.getResponse().getContentAsString());
    }

    @Test
    public void studioNotFound() throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("id", 9999);

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString("/studios/{id}");

        mockMvc.perform(get(uriBuilder
                        .buildAndExpand(param)
                        .toUri()))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
