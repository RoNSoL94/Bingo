package utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public interface SceneController {

    /**
     * This method allows to change scene
     * the parameter passed is needed to
     * locate in wich controller you are
     * going to use it
     * @param obj
     */
    static void changeScene(Object obj, String path) {
        try {
            Parent root = FXMLLoader.load((obj.getClass().getResource(".." + path)));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(windowEvent -> System.exit(0));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
