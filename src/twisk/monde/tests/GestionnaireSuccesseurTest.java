package twisk.monde.tests;

import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.GestionnaireSuccesseur;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireSuccesseurTest {
    GestionnaireSuccesseur gS;
    Etape e1;
    Etape e2;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        gS = new GestionnaireSuccesseur();
        e1 = new Activite("test");
        e2 = new Guichet("guichet");
    }

    @org.junit.jupiter.api.Test
    void ajouter() {
        gS.ajouter(e1,e2);
        assertTrue(gS.getSucc(0).toString().equals("test"));
        assertTrue(gS.getSucc(1).toString().equals("guichet"));
    }

    @org.junit.jupiter.api.Test
    void nbEtapes() {
        assertEquals(0,gS.nbEtapes());
        gS.ajouter(e1,e2);
        assertEquals(2,gS.nbEtapes());
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        gS.ajouter(e1,e2);
    }
}