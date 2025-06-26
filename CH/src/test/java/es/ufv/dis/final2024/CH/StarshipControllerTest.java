package es.ufv.dis.final2024.CH;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StarshipController.class)
class StarshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllStarshipsReturnsOk() throws Exception {
        mockMvc.perform(get("/starships"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetStarshipByNameFound() throws Exception {
        mockMvc.perform(get("/starships/Slave 1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Slave 1"));
    }

    @Test
    void testGetStarshipByNameNotFound() throws Exception {
        mockMvc.perform(get("/starships/NonExistentShip"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGenerarPDF() throws Exception {
        RequestDTO dto = new RequestDTO("Slave 1");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/starships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("PDF generado correctamente")));
    }
}
