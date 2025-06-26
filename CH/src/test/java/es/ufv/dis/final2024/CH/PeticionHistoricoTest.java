package es.ufv.dis.final2024.CH;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeticionHistoricoTest {

    @Test
    void testConstructorAndGetters() {
        PeticionHistorico ph = new PeticionHistorico("Imperial Shuttle", 3);
        assertEquals("Imperial Shuttle", ph.getShipName());
        assertEquals(3, ph.getCount());
    }

    @Test
    void testSetters() {
        PeticionHistorico ph = new PeticionHistorico();
        ph.setShipName("Star Destroyer");
        ph.setCount(5);
        assertEquals("Star Destroyer", ph.getShipName());
        assertEquals(5, ph.getCount());
    }

    @Test
    void testDefaultConstructor() {
        PeticionHistorico ph = new PeticionHistorico();
        assertNull(ph.getShipName());
        assertEquals(0, ph.getCount());
    }

    @Test
    void testSetCountZero() {
        PeticionHistorico ph = new PeticionHistorico("Falcon", 2);
        ph.setCount(0);
        assertEquals(0, ph.getCount());
    }
}
