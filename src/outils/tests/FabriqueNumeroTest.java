package outils.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FabriqueNumeroTest {
    FabriqueNumero fabrique;

    @BeforeEach
    void setUp() {
        fabrique = FabriqueNumero.getInstance();
    }

    @Test
    void getNumeroEtape() {
        assertEquals(0, fabrique.getNumeroEtape());
        assertEquals(1, fabrique.getNumeroEtape());
    }

    @Test
    void getNumeroSemaphore() {
        assertEquals(1, fabrique.getNumeroSemaphore());
        assertEquals(2, fabrique.getNumeroSemaphore());
    }

    @Test
    void reset() {
        assertEquals(1, fabrique.getNumeroSemaphore());
        assertEquals(0, fabrique.getNumeroEtape());
        assertEquals(1, fabrique.getNumeroEtape());

        fabrique.reset();

        assertNotEquals(1, fabrique.getNumeroEtape());
    }
}