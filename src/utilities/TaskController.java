package utilities;

import gameBase.GameControls;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

public interface TaskController {
    /**
     * This method allow to have a thread relate to the
     * gui thread running in background in low priority
     * @param runnable
     * @param sleepTime
     */
    @FXML
    static void backgroundTask(Runnable runnable, int sleepTime) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (GameControls.isRUnning) {
                    Platform.runLater(runnable);
                    Thread.sleep(sleepTime);
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }
}
