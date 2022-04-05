package twisk.mondeIG.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;

class ActiviteIGTest {
    EtapeIG etape;
    @BeforeEach
    void setUp() {
        etape = new ActiviteIG("test1", FabriqueIdentifiant.getInstance().getIdentifiantEtape(),
                TailleComposants.getInstance().getLargeurActivite(),
                TailleComposants.getInstance().getHauteurActivite());
    }

    @Test
    void Iteration() {
        System.out.println("Etape : " + etape);
        for (PointDeControleIG controle: etape) {
            System.out.println("posX = " + controle.getPosX() + "\tposY = " + controle.getPosY());

        }
    }
}