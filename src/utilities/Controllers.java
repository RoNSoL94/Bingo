package utilities;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public interface Controllers {

    /**
     * This method allow to have a thread relate to the
     * gui thread running in background in low priority
     * @param runnable
     * @param sleepTime
     */
    @FXML
     static void backgroundTask(Runnable runnable,int sleepTime) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Platform.runLater(runnable);
                    Thread.sleep(sleepTime);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * This method allows to change scene
     * the parameter passed is needed to
     * locate in wich controller you are
     * going to use it
     * @param obj
     */
    static void changeScene(Object obj){
        try {
            Parent root = FXMLLoader.load((obj.getClass().getResource("../fxmlFiles/insider.fxml")));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
