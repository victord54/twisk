#include "def.h"

int main(int argc, const char *argv[]) {
  // client.c
  /*
  int tab_jetons_guichet[1] = {3};
  int nb_etapes = 4;
  int nb_guichets = 1;
  int nb_clients = 8;
  */

  // monde-test-1.c
  int tab_jetons_guichet[2] = {1,2};
  int nb_etapes = 7;
  int nb_guichets = 2;
  int nb_clients = 5;
  
  int *tab = start_simulation(nb_etapes, nb_guichets, nb_clients, tab_jetons_guichet);
  int *tab_client = ou_sont_les_clients(nb_etapes, nb_clients);
    
  // Affichage des PID des clients
  printf("les clients : ");
  for (int i = 0; i < nb_clients; i ++) {
    printf("%d ", tab[i]);
  }
  printf("\n");
  sleep(1);

  // Affichage des PID des clients par étape
  while (tab_client[(nb_etapes-1)*nb_clients + nb_etapes - 1] != nb_clients) { // [18] = 5
    system("clear");
    tab_client = ou_sont_les_clients(nb_etapes, nb_clients);
    int decalage = 0;
    int nb_a_afficher = tab_client[0];
    for (int n = 0; n < nb_etapes; n++) {
      printf("étape %d | %d clients : ", n, nb_a_afficher);
      for (int i = decalage +1; i < decalage + 1 + nb_a_afficher; i++) {
        printf("%d ", tab_client[i]);
      }
      printf("\n");
      decalage +=nb_clients+1;
      nb_a_afficher = tab_client[decalage];
    }
    // printf("------------------------\n");
    // for (int i = 0; i < 100; i++) {
    //   printf("%d ",tab_client[i]);
    // }
    printf("\n");
    sleep(1);
  }
  nettoyage();
  return 0;
}
