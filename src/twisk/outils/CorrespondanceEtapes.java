package twisk.outils;

import twisk.monde.Etape;
import twisk.mondeIG.EtapeIG;

import java.util.HashMap;

public class CorrespondanceEtapes {
    protected HashMap<EtapeIG,Etape> etapes;

    public CorrespondanceEtapes(){
        etapes = new HashMap<>();
    }

    public void ajouter(EtapeIG etig, Etape e){
        etapes.put(etig,e);
    }

    public Etape getEtape(EtapeIG e){
        return etapes.get(e);
    }
}
