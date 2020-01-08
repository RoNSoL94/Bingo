package home.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainFrameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button playBtn;

    @FXML
    private Button optionsBtn;

    @FXML
    private Button ratioBtn;

    @FXML
    private Button balanceBtn;

    @FXML
    private Button buyBtn;

    @FXML
    void initialize() {
        playBtn.setOnAction((ev) -> changeScene(playBtn));


    }

    public void changeScene(Button btn){
        btn.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load((getClass().getResource("../fxmlFiles/insider.fxml")));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}