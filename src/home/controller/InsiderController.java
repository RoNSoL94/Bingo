package home.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gameBase.BingoCard;
import home.userProperties.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class InsiderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitBtn;

    @FXML
    private Button ternoBtn;

    @FXML
    private Button quaternaBtn;

    @FXML
    private Button cinquinaBtn;

    @FXML
    private Button tombolaBtn;

    @FXML
    private Button moneyBtn;

    @FXML
    private FlowPane playerCard;

    private BingoCard card = new BingoCard();

    @FXML
    void initialize() {
        Player player = new Player(
                "andrea","skyires","andrea.luiu@libero.it","and",22,card);
        player.getBingoCard().creationCard();

        cards(player.getBingoCard().getCard());

        exitBtn.setOnAction((ev) -> changeScene(exitBtn));
    }

    @FXML
    public void cards(String arr [][]){
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                playerCard.getChildren().add(new Button(arr[i][j]));
            }
        }
    }
    public void changeScene(Button btn){
        btn.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load((getClass().getResource("../fxmlFiles/mainFrame.fxml")));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
