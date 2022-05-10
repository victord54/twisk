package twisk.outils;

import twisk.monde.Etape;
import twisk.mondeIG.EtapeIG;

import java.util.HashMap;
import java.util.Objects;

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

    public EtapeIG getEtapeIG(Etape e){
        for (HashMap.Entry<EtapeIG,Etape> ent : etapes.entrySet()){
            if (Objects.equals(e,ent.getValue())){
                return ent.getKey();
            }
        }
        return null;
    }

    public void remove(EtapeIG e){
        etapes.remove(e);
    }
}
