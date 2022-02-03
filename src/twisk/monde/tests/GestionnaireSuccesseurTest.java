package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.GestionnaireSuccesseur;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestionnaireSuccesseurTest {
    private GestionnaireSuccesseur gS;
    private Etape e1;
    private Etape e2;

    @BeforeEach
    void setUp() {
        gS = new GestionnaireSuccesseur();
        e1 = new Activite("test");
        e2 = new Guichet("guichet");
    }

    @Test
    void ajouter() {
        gS.ajouter(e1,e2);
        assertTrue(gS.getSucc(0).toString().equalsIgnoreCase("test"));
        assertTrue(gS.getSucc(1).toString().equalsIgnoreCase("guichet"));
    }

    @Test
    void nbEtapes() {
        assertEquals(0,gS.nbEtapes());
        gS.ajouter(e1,e2);
        assertEquals(2,gS.nbEtapes());
    }

    @Test
    void iterator() {
        gS.ajouter(new Activite("e"),new Activite("ee"),new Guichet("eee"));
        StringBuilder s = new StringBuilder();
        s.append("e");
        for (Etape e : gS){
            assertTrue(s.toString().equalsIgnoreCase(e.toString()));
            s.append("e");
        }
    }

    @Test
    void string(){
        gS.ajouter(e1,e2);
        System.out.println(gS.toString());
    }
}