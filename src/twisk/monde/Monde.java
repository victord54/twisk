package twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    protected SasSortie sortie;
    protected SasEntree entree;
    protected GestionnaireEtapes gestionnaireEtape;


    public Monde(){
        gestionnaireEtape = new GestionnaireEtapes();
    }

    public void aCommeEntree(Etape ... etapes){
        
    }

    public void aCommeSortie(Etape ... etapes){

    }

    public void ajouter(Etape ... etapes){
        gestionnaireEtape.ajouter(etapes);
        
    }

    public int nbEtapes(){
        return gestionnaireEtape.nbEtapes();

    }

    public int nbGuichets(){

    }

    public Iterator<Etape> iterator(){
        return gestionnaireEtape.iterator();
    }
}
