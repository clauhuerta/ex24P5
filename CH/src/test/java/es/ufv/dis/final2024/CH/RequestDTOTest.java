package es.ufv.dis.final2024.CH;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestDTOTest {

    @Test
    void testConstructorAndGetter() {
        RequestDTO dto = new RequestDTO("Millennium Falcon");
        assertEquals("Millennium Falcon", dto.getShip());
    }

    @Test
    void testSetter() {
        RequestDTO dto = new RequestDTO();
        dto.setShip("X-Wing");
        assertEquals("X-Wing", dto.getShip());
    }

    @Test
    void testDefaultConstructor() {
        RequestDTO dto = new RequestDTO();
        assertNull(dto.getShip());
    }

    @Test
    void testSetNull() {
        RequestDTO dto = new RequestDTO();
        dto.setShip(null);
        assertNull(dto.getShip());
    }
}
