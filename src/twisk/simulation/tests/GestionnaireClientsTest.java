package twisk.simulation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireClientsTest {
    private GestionnaireClients gestionnaire;
    @BeforeEach
    void setUp() {
        gestionnaire = new GestionnaireClients();
    }

    @Test
    void setClientsAndSetNbClientsAndIterator() {
        gestionnaire.setClients(56, 57, 58, 59, 60);
        int n = 56;
        for (Client c: gestionnaire) {
            assertEquals(n, c.getNumeroClient());
            n++;
        }
    }
}