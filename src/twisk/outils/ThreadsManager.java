/**
 * Singleton de management des threads.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.outils;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class ThreadsManager {
    /**
     * Instance pour le singleton.
     */
    public static final ThreadsManager instance = new ThreadsManager();

    /**
     * Champ correspondant à la liste des threads.
     */
    ArrayList<Thread> threads = new ArrayList<>();

    /**
     * Constructeur.
     */
    private ThreadsManager() {
    }

    /**
     * Getter donnant l'instance du singleton.
     *
     * @return instance.
     */
    public static ThreadsManager getInstance() {
        return instance;
    }

    /**
     * Méthode permettant de lancer une Task passée en paramètre dans un nouveau thread.
     * @param task La Task à lancer.
     */
    public void lancerTask(Task task) {
        Thread th = new Thread(task);
        //System.out.println(th.isAlive()); //-->Return false

        threads.add(th);
        th.start();
        //System.out.println(th.isAlive());//--> return true
    }

    /**
     * Méthode permettant de détruire tous les threads.
     */
    public void detruiretout() {
        for (Thread thread: threads) {
            thread.stop();
        }
    }
}
