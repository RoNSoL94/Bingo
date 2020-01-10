package home.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import utilities.SceneController;

public class MainFrameController {

    @FXML
    private AnchorPane mainframeBackground;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button playBtn,optionsBtn,ratioBtn
            ,balanceBtn,buyBtn;

    @FXML
    void initialize() {
        playBtn.setOnAction((ev) -> {
            playBtn.getScene().getWindow().hide();
            //path to update
            SceneController.changeScene(this,"/fxmlFiles/insider.fxml");
        });

    }
}