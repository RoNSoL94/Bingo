package home.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import utilities.SceneController;

public class MainFrameController {
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
                SceneController.changeScene(this, "/fxmlFiles/insider.fxml");
                prova = nickField.getText();
            }else System.out.println("error");
        });
    }
}
