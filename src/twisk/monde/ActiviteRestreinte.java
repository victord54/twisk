package twisk.monde;

public class ActiviteRestreinte extends Activite {
    private int numSemGuichet;

    public ActiviteRestreinte(String nom) {
        super(nom);
    }

    public ActiviteRestreinte(String nom, int t, int e) {
        super(nom, t, e);
    }

    public void aCommeGuichet(int semaphore){
        this.numSemGuichet = semaphore;
    }

    public String toC(){
        StringBuilder str = new StringBuilder();
        str.append("delai(")
    }

}
