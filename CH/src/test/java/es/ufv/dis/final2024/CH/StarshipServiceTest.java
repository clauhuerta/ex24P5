package es.ufv.dis.final2024.CH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StarshipServiceTest {

    private StarshipService service;

    @BeforeEach
    void setup() {
        service = new StarshipService();
    }

    @Test
    void testGetAllStarships() throws Exception {
        List<Starship> starships = service.getAllStarships();
        assertNotNull(starships);
        assertTrue(starships.size() > 0);
    }

    @Test
    void testGetStarshipByNameFound() throws Exception {
        Optional<Starship> found = service.getStarshipByName("Slave 1");
        assertTrue(found.isPresent());
        assertEquals("Slave 1", found.get().getName());
    }

    @Test
    void testGetStarshipByNameNotFound() throws Exception {
        Optional<Starship> found = service.getStarshipByName("NonExistentShip");
        assertFalse(found.isPresent());
    }

    @Test
    void testGenerarPdfYHistorico() throws Exception {
        Starship s = service.getAllStarships().get(0);
        service.generarPdf(s);
        // Verifica que el PDF existe
        String pdfPath = "naves/" + s.getName().replace(" ", "_") + ".pdf";
        assertTrue(new File(pdfPath).exists());
        // Verifica que el histórico se actualiza
        assertTrue(new File("peticiones/peticiones.json").exists());
        // Limpieza
        new File(pdfPath).delete();
    }
}
