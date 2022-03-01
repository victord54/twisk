#include "def.h"

// Constantes de client.c
#define ENTREE 0
#define GUICHET 1
#define ACTIVITE 2
#define SORTIE 3

#define SEMAPHORE 1

void simulation(int ids) {
    entrer(ENTREE);
    delai(3, 1);

    transfert(ENTREE, GUICHET);
    P(ids, SEMAPHORE);
    transfert(GUICHET, ACTIVITE);

    delai(3,2);
    V(ids, SEMAPHORE);
    transfert(ACTIVITE, SORTIE);
}
