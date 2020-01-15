package home.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.SceneController;

public class MainFrameController implements SceneController {
    @FXML
    private Button playBtn, helpBtn;

    @FXML
    private TextField nickField;

    public static String prova;

    @FXML
    void initialize() {
        playBtn.setOnAction((ev) -> {
            if (!nickField.getText().equals("")) {
                playBtn.getScene().getWindow().hide();
                //path to update
                SceneController.changeScene(this,"/fxmlFiles/insider.fxml");
                prova = nickField.getText();
            }else System.out.println("error");
        });

        helpBtn.setOnAction(ev -> uponScene("../fxmlFiles/help.fxml"));

    }

    /**
     * Overlap the scene on another
     * @param path
     */
    @Override
     public void uponScene(String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
