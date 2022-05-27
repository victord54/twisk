package twisk.outils;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class ThreadsManager {

    public static final ThreadsManager instance = new ThreadsManager();
    ArrayList<Thread> threads = new ArrayList<>();

    private ThreadsManager() {

    }

    public static ThreadsManager getInstance() {
        return instance;
    }

    public void lancerTask(Task task) {
        Thread th = new Thread(task);
        //System.out.println(th.isAlive()); //-->Return false

        threads.add(th);
        th.start();
        //System.out.println(th.isAlive());//--> return true
    }

    public void detruiretout() {
        for (Thread thread: threads) {
            thread.stop();
        }
    }
}
