package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import outils.FabriqueNumero;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EtapeTest {
    private Etape etape1;
    private Etape etape2;
    private FabriqueNumero fabrique;

    @BeforeEach
    void setUp() {
        fabrique = FabriqueNumero.getInstance();
        etape1 = new Activite("Test",fabrique.getNumeroEtape());
        etape2 = new Guichet("Test aussi",fabrique.getNumeroEtape());
    }

    @Test
    void ajouterSuccesseur() {
        etape1.ajouterSuccesseur(new Activite("te",fabrique.getNumeroEtape()), new Activite("tess",fabrique.getNumeroEtape()), new Guichet("eez",fabrique.getNumeroEtape()));
        assertEquals(3, etape1.nbSuccesseurs());
    }

    @Test
    void iterator() {
        etape1.ajouterSuccesseur(new Activite("e",fabrique.getNumeroEtape()), new Activite("ee",fabrique.getNumeroEtape()), new Guichet("eee",fabrique.getNumeroEtape()));
        StringBuilder s = new StringBuilder("e");
        for (Etape etape : etape1) {
            assertTrue(s.toString().equalsIgnoreCase(etape.getNom()));
            s.append("e");
        }
    }

    @Test
    void testNumEtape(){
        assertEquals(etape1.getNumEtape(),0);
        assertEquals(etape2.getNumEtape(),1);
        fabrique.reset();

        Etape e3 = new Guichet("ee",fabrique.getNumeroEtape());
        assertEquals(e3.getNumEtape(),0);
    }
}