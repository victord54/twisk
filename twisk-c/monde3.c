#include "def.h"

// Constantes de monde-test-1.c
#define ENTREE 0
#define GUICHET1 1
#define ACTIVITE1 2
#define ACTIVITE2 3
#define GUICHET2 4
#define ACTIVITE3 5
#define SORTIE 6

#define SEMAPHORE1 1
#define SEMAPHORE2 2

void simulation(int ids) {
    entrer(ENTREE);
    delai(3, 2);
    transfert(ENTREE, GUICHET1);
    P(ids, SEMAPHORE1);
    transfert(GUICHET1, ACTIVITE1);
    delai(3,2);
    V(ids, SEMAPHORE1);
    transfert(ACTIVITE1, ACTIVITE2);
    delai(5,2);
    transfert(ACTIVITE2, GUICHET2);
    P(ids, SEMAPHORE2);
    transfert(GUICHET2, ACTIVITE3);
    delai(3,1);
    V(ids, SEMAPHORE2);
    transfert(ACTIVITE3, SORTIE);
}
