package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;

import static org.junit.jupiter.api.Assertions.*;

class MondeTest {
    private Monde monde;
    private Monde monde2;

    @BeforeEach
    public void setUp() {
        monde = new Monde();
        monde2 = new Monde();
    }

    @Test
    public void aCommeEntree() {
        int nbEntree = monde.getSasEntree().nbSuccesseurs();
        assertEquals(0, nbEntree);
        monde.aCommeEntree(new Activite("e"), new Activite("ee"));
        nbEntree = monde.getSasEntree().nbSuccesseurs();
        assertEquals(2, nbEntree);
    }

    @Test
    public void aCommeSortie() {
        Etape e1 = new Activite("e");
        Etape e2 = new Activite("ee");
        Etape e3 = new Activite("eee");
        Etape e4 = new Activite("eeee");
        monde.ajouter(e1, e2, e3, e4);
        monde.aCommeSortie(e3, e4);

        assertTrue(e3.estUneSortie());
        assertTrue(e4.estUneSortie());
        assertFalse(e1.estUneSortie());

    }

    @Test
    public void ajouter() {
        assertEquals(2, monde.nbEtapes());
        monde.ajouter(new Activite("e"), new Activite("ee"), new Activite("eee"));
        assertEquals(5, monde.nbEtapes());
    }

    @Test
    public void nbEtapes() {
        assertEquals(2, monde.nbEtapes());
        monde.ajouter(new Activite("e"), new Activite("ee"), new Activite("eee"));
        assertEquals(5, monde.nbEtapes());
    }

    @Test
    public void nbGuichets() {
        assertEquals(0, monde.nbGuichets());
        monde.ajouter(new Activite("e"), new Guichet("ee"), new Activite("eee"));
        assertEquals(1, monde.nbGuichets());
        monde2.ajouter(new Guichet("e"), new Guichet("ee"), new Guichet("eee"));
        assertEquals(3, monde2.nbGuichets());

    }

    @Test
    public void toCTest() {
        Etape e1 = new Activite("oui");
        monde.aCommeEntree(e1);
        monde.aCommeSortie(e1);
        monde.ajouter(e1);
        System.out.println(monde.toC());
    }

    @Test
    public void testToString() {
        Etape e1 = new Activite("e");
        Etape e2 = new Activite("ee");
        Etape e3 = new Activite("eee");
        Etape e4 = new Activite("eeee");
        e1.ajouterSuccesseur(e2, e3);
        e3.ajouterSuccesseur(e4);
        e2.ajouterSuccesseur(e3);
        monde.ajouter(e1, e2, e3, e4);
        System.out.println(monde.toString());
    }
}