package twisk.outils.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.outils.KitC;

import static org.junit.jupiter.api.Assertions.*;

class KitCTest {
    KitC kit;
    @BeforeEach
    void setUp() {
        kit = new KitC();
    }

    @Test
    void creerEnvironnement() {
        kit.creerEnvironnement();
    }
}