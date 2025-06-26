package es.ufv.dis.final2024.CH;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PDFManagerTest {

    @Test
    void testCrearPDFCreatesFile() throws Exception {
        Starship s = new Starship("TestNave", "M1", "1", "1", "100", "1 day", "1.0", "TestClass", Arrays.asList(), Arrays.asList("f1"));
        String path = "naves/TestNave_test.pdf";
        PDFManager.crearPDF(path, s);
        File file = new File(path);
        assertTrue(file.exists() && file.length() > 0);
        file.delete();
    }

    @Test
    void testCrearPDFNoPilots() throws Exception {
        Starship s = new Starship("NPilot", "M2", "2", "2", "200", "2 days", "2.0", "Class2", null, Arrays.asList("f1","f2"));
        String path = "naves/NPilot.pdf";
        PDFManager.crearPDF(path, s);
        File file = new File(path);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    void testCrearPDFNullFilms() throws Exception {
        Starship s = new Starship("NoFilms", "M3", "3", "3", "300", "3 days", "3.0", "Class3", Arrays.asList(), null);
        String path = "naves/NoFilms.pdf";
        PDFManager.crearPDF(path, s);
        File file = new File(path);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    void testCrearPDFThrowsNoFile() {
        assertThrows(Exception.class, () -> {
            PDFManager.crearPDF("/no/exists/path/file.pdf", new Starship());
        });
    }
}
