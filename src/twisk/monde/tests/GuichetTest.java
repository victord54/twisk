package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Etape;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuichetTest {
    private Etape etape;

    @BeforeEach
    void setUp() {
        etape = new Guichet("test");
    }

    @Test
    void estUnGuichet() {
        assertTrue(etape.estUnGuichet());
        assertFalse(etape.estUneActivite());
    }
}