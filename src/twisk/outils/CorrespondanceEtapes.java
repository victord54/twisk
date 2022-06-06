/**
 * Classe permettant la correspondance entre étapeIG et étape.
 *
 * @author Victor Dallé et Claire Kurth
 */
package twisk.outils;

import twisk.monde.Etape;
import twisk.mondeIG.EtapeIG;

import java.util.HashMap;
import java.util.Objects;

public class CorrespondanceEtapes {
    /**
     * Champ correspondant à une HashMap dont chaque identifiant correspondant à une EtapeIG et sa valeur à son Etape correspondante.
     */
    protected HashMap<EtapeIG,Etape> etapes;

    /**
     * Constructeur par défaut.
     */
    public CorrespondanceEtapes(){
        etapes = new HashMap<>();
    }

    /**
     * Méthode permettant d'ajouter une étapeIG et son étape correspondante.
     *
     * @param etig L'étapeIG.
     * @param e L'étape correspondante.
     */
    public void ajouter(EtapeIG etig, Etape e){
        etapes.put(etig,e);
    }

    /**
     * Getter de l'Etape correspondante à l'EtapeIG passée en paramètre.
     *
     * @param e EtapeIG correspondante à l'Etape que l'on veut récupérer.
     * @return L'Etape correspondante.
     */
    public Etape getEtape(EtapeIG e){
        return etapes.get(e);
    }

    /**
     * Getter de l'EtapeIG correspondante à l'Etape passée en paramètre.
     *
     * @param e Etape correspondante à l'EtapeIG que l'on veut récupérer.
     * @return L'EtapeIG correspondante.
     */
    public EtapeIG getEtapeIG(Etape e){
        for (HashMap.Entry<EtapeIG,Etape> ent : etapes.entrySet()){
            if (Objects.equals(e,ent.getValue())){
                return ent.getKey();
            }
        }
        return null;
    }
}
