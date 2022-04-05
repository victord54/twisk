package twisk.exceptions;

public abstract class TwiskException extends Exception {
    public TwiskException(String s) {
        super(s);
    }

    public abstract void afficherMessage();
}
