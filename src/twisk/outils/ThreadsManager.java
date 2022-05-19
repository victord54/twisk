package twisk.outils;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class ThreadsManager {

    public static final ThreadsManager instance = new ThreadsManager();
    ArrayList<Thread> threads;

    private ThreadsManager() {}

    public static ThreadsManager getInstance() {
        return instance;
    }

    public void lancerTask(Task task) {
        task.run();
    }

    public void detruiretout() {
        for (Thread thread: threads) {
            thread.stop();
        }
    }
}
