package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActiviteTest {
    private Etape etape;
    private Etape et1;

    @BeforeEach
    void setUp() {
        etape = new Activite("test");
        et1 = new Activite("testttt");
        etape.ajouterSuccesseur(et1);
    }

    @Test
    void estUneActivite() {
        assertTrue(etape.estUneActivite());
        assertFalse(etape.estUnGuichet());
    }

    @Test
    void toC() {
        System.out.println(etape.toC());
    }
}