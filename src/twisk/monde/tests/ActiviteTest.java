package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActiviteTest {
    private Etape etape;

    @BeforeEach
    void setUP() {
        etape = new Activite("test");
    }

    @Test
    void estUneActivite() {
        assertTrue(etape.estUneActivite());
        assertFalse(etape.estUnGuichet());
    }
}