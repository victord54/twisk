package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.GestionnaireEtapes;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireEtapesTest {
    private GestionnaireEtapes gE;
    Etape e1;
    Etape e2;

    @BeforeEach
    void setUp(){
        gE = new GestionnaireEtapes();
        e1 = new Activite("test");
        e2 = new Guichet("guigui");
    }

    @Test
    void ajouter() {
        gE.ajouter(e1,e2);
        assertTrue(gE.getEtape(0).toString().equalsIgnoreCase("test"));
        assertTrue(gE.getEtape(1).toString().equalsIgnoreCase("guigui"));
    }

    @Test
    void nbEtapes() {
        assertEquals(0,gE.nbEtapes());
        gE.ajouter(e1,e2);
        assertEquals(2,gE.nbEtapes());
    }

    @Test
    void iterator() {
        gE.ajouter(new Activite("e"),new Activite("ee"),new Guichet("eee"));
        StringBuilder s = new StringBuilder();
        s.append("e");
        for (Etape e : gE){
            assertTrue(s.toString().equalsIgnoreCase(e.toString()));
            s.append("e");
        }
    }
}