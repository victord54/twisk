package twisk.outils.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.outils.FabriqueIdentifiant;

class FabriqueIdentifiantTest {
    private FabriqueIdentifiant fabrique;

    @BeforeEach
    void setUp() {
        fabrique = FabriqueIdentifiant.getInstance();
    }

    @Test
    void getInstance() {
        for (int i = 0; i < 10; i++) {
            System.out.println(fabrique.getIdentifiantEtape());
        }
        FabriqueIdentifiant fabriqueBis = FabriqueIdentifiant.getInstance();
        for (int i = 0; i < 10; i++) {
            System.out.println(fabriqueBis.getIdentifiantEtape());
        }
    }
}