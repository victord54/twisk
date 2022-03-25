package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;
import twisk.outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.*;

class MondeTest {
    private Monde monde;
    private Monde monde2;

    @BeforeEach
    public void setUp() {
        FabriqueNumero.getInstance().reset();
        monde = new Monde();

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

    //@Test
    public void nbGuichets() {
        assertEquals(0, monde.nbGuichets());
        monde.ajouter(new Activite("e"), new Guichet("ee"), new Activite("eee"));
        assertEquals(1, monde.nbGuichets());
        monde2.ajouter(new Guichet("e"), new Guichet("ee"), new Guichet("eee"));
        assertEquals(3, monde2.nbGuichets());

    }

    @Test
    public void testToString() {
        Etape e1 = new Activite("e");
        Etape e2 = new Activite("ee");
        Etape e3 = new Activite("eee");
        Etape e4 = new Activite("eeee");
        e1.ajouterSuccesseur(e2);
        e2.ajouterSuccesseur(e3);
        e3.ajouterSuccesseur(e4);
        monde.ajouter(e1, e2, e3, e4);
        monde.aCommeEntree(e1);
        monde.aCommeSortie(e4);
        System.out.println(monde.toString());
    }


    @Test
    public void toCTest() {
        Etape e1 = new Activite("e");
        Etape e2 = new Activite("ee");
        Etape e3 = new Activite("eee");
        Etape e4 = new Activite("eeee");
        e1.ajouterSuccesseur(e2);
        e2.ajouterSuccesseur(e3);
        e3.ajouterSuccesseur(e4);
        monde.ajouter(e1, e2, e3, e4);
        monde.aCommeEntree(e1);
        monde.aCommeSortie(e4);
        System.out.println(monde.toC());
    }

    @Test
    public void toCTestGuichet() {
        Guichet e1 = new Guichet("ee");
        ActiviteRestreinte e2 = new ActiviteRestreinte("eee");
        e2.setSemaphoreGuichet(e1.getNumSemaphore());
        e1.ajouterSuccesseur(e2);
        monde.ajouter(e1, e2);
        monde.aCommeEntree(e1);
        monde.aCommeSortie(e2);
        System.out.println(monde.toC());
    }


}