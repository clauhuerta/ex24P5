package es.ufv.dis.final2024.CH;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StarshipTest {

    @Test
    void testConstructorAndGetters() {
        Starship s = new Starship("Slave 1", "ModelX", "999", "2", "800", "1 month", "1.0", "Fighter", Arrays.asList("p1", "p2"), Arrays.asList("f1", "f2"));
        assertEquals("Slave 1", s.getName());
        assertEquals("ModelX", s.getModel());
        assertEquals("999", s.getCost_in_credits());
        assertEquals("2", s.getCrew());
        assertEquals("800", s.getCargo_capacity());
        assertEquals("1 month", s.getConsumables());
        assertEquals("1.0", s.getHyperdrive_rating());
        assertEquals("Fighter", s.getStarship_class());
        assertEquals(2, s.getPilots().size());
        assertEquals(2, s.getFilms().size());
    }

    @Test
    void testSetters() {
        Starship s = new Starship();
        s.setName("Test");
        s.setModel("M1");
        s.setCost_in_credits("10");
        s.setCrew("5");
        s.setCargo_capacity("400");
        s.setConsumables("2 days");
        s.setHyperdrive_rating("2.0");
        s.setStarship_class("Class A");
        s.setPilots(Arrays.asList("p3"));
        s.setFilms(Arrays.asList("f3"));
        assertEquals("Test", s.getName());
        assertEquals("M1", s.getModel());
        assertEquals("10", s.getCost_in_credits());
        assertEquals("5", s.getCrew());
        assertEquals("400", s.getCargo_capacity());
        assertEquals("2 days", s.getConsumables());
        assertEquals("2.0", s.getHyperdrive_rating());
        assertEquals("Class A", s.getStarship_class());
        assertEquals(1, s.getPilots().size());
        assertEquals(1, s.getFilms().size());
    }

    @Test
    void testEqualsDifferentObjects() {
        Starship s1 = new Starship();
        Starship s2 = new Starship();
        assertNotSame(s1, s2);
    }

    @Test
    void testNullFields() {
        Starship s = new Starship();
        assertNull(s.getName());
        assertNull(s.getPilots());
        assertNull(s.getFilms());
    }
}
