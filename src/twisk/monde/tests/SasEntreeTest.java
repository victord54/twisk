package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.SasEntree;

class SasEntreeTest {
    Etape entree;

    @BeforeEach
    void setUp() {
        entree = new SasEntree();
        entree.ajouterSuccesseur(new Activite("test"));
    }

    @Test
    void toC() {
        System.out.println(entree.toC());
    }
}