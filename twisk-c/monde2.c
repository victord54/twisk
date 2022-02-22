#include "def.h"

#define ENTREE 0
#define GUICHET_1 1
#define ACTIVITE_1 2
#define ACTIVITE_2 3
#define GUICHET_2 4
#define ACTIVITE_3 5
#define ACTIVITE_4 6
#define SORTIE 7

#define NUM_SEM_GUICHET_1 1
#define NUM_SEM_GUICHET_2 2

void simulation(int ids){
    entrer(ENTREE);
    delai(4,2);

    transfert(ENTREE,GUICHET_1);
    P(ids,NUM_SEM_GUICHET_1);
    transfert(GUICHET_1,ACTIVITE_1);
    delai(8,2);
    V(ids, NUM_SEM_GUICHET_1);

    transfert(ACTIVITE_1,ACTIVITE_2);
    delai(7,3);

    transfert(ACTIVITE_2,GUICHET_2);
    P(ids, NUM_SEM_GUICHET_2);
    transfert(GUICHET_2,ACTIVITE_3);
    delai(5,1);
    V(ids, NUM_SEM_GUICHET_2);

    transfert(ACTIVITE_3,ACTIVITE_4);
    delai(8,2);

    transfert(ACTIVITE_4,SORTIE);
}
