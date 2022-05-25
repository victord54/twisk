package twisk.mondeIG.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

class MondeIGTest {
    private MondeIG monde;
    @BeforeEach
    void setUp() {
        monde = new MondeIG();
    }

    @Test
    void ajouter() {
        monde.ajouter("Activité");
        monde.ajouter("Activité");
        monde.ajouter("Activité");
        for (EtapeIG etapeIG: monde) {
            System.out.println(etapeIG);
        }
    }

    @Test
    void sauvegarder(){
        monde.ajouter("Activité");
        monde.ajouter("Guichet");
        monde.ajouter("Activité");
        monde.sauvegarder();
    }

    @Test
    void ouvrir(){
        monde.ouvrir("test.json");
        for (EtapeIG e : monde){
            System.out.println(e.toString());
        }
    }
}