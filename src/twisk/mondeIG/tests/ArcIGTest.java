package twisk.mondeIG.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.PointDeControleIG;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArcIGTest {
    ArcIG arc;
    PointDeControleIG pt1, pt2, pt3, pt4;
    EtapeIG etape;

    @BeforeEach
    void setUp() {
        etape = new ActiviteIG("Test", "id1", 10, 10);
        pt1 = new PointDeControleIG(0, 0, etape);
        pt2 = new PointDeControleIG(10, 10, etape);
        pt3 = new PointDeControleIG(20, 20, etape);
        pt4 = new PointDeControleIG(30, 30, etape);
    }

    @Test
    void isDoublon() {
        arc = new ArcIG(pt1, pt2);

        assertFalse(arc.isDoublon(pt3, pt4));
        assertTrue(arc.isDoublon(pt1, pt2));
        assertTrue(arc.isDoublon(pt2, pt1));
    }
}