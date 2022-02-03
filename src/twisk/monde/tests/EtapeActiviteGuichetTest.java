package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EtapeActiviteGuichetTest {
    Etape etape1;
    Etape etape2;

    @BeforeEach
    void setUp() {
        etape1 = new Activite("Test");
        etape2 = new Guichet("Test aussi");
    }

    @Test
    void ajouterSuccesseur() {
        etape1.ajouterSuccesseur(new Activite("te"), new Activite("tess"), new Guichet("eez"));
        System.out.println(etape1.nbSuccesseurs());
        assertEquals(3, etape1.nbSuccesseurs());
    }

    @Test
    void estUneActivite() {
    }

    @Test
    void estUnGuichet() {
    }

    @Test
    void iterator() {
    }
}