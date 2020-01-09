package home.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import gameBase.BingoCard;
import gameBase.GameControls;
import gameBase.GameFunction;
import home.userProperties.Player;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import utilities.Controllers;

public class InsiderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitBtn,ternoBtn,startBtn,
            quaternaBtn,cinquinaBtn,passerBtn;

    @FXML
    private FlowPane playerCard;
    //TO CHANGE USED JUST SEE
    private BingoCard card = new BingoCard();
    GameControls controls = new GameControls();
    GameFunction game = new GameFunction();
    private static int counter = 0;

    @FXML
    void initialize() {
        passerBtn.setVisible(false);

        //change scene
        exitBtn.setOnAction((ev) -> {
            exitBtn.getScene().getWindow().hide();
            Controllers.changeScene(this);
        });
        //starts the game
        startBtn.setOnAction((ev) -> {
            startBtn.setVisible(false);
            passerBtn.setVisible(true);
            //leaving the rewards once won
            Controllers.backgroundTask(() -> {
                if (GameControls.ternoWon) ternoBtn.setVisible(false);
                if (GameControls.quaternaWon) quaternaBtn.setVisible(false);
                if (GameControls.cinquinaWon) cinquinaBtn.setVisible(false);
            }, 2000);

            //optimizable
            Player player1 = new Player(
                    "player1", "player1", "none", "none", 22, card);
            player1.getBingoCard().creationCard();
            Player player2 = new Player(
                    "player2", "player2", "none", "none", 22, card);
            player2.getBingoCard().creationCard();
            Player player3 = new Player(
                    "player3", "player3", "none", "none", 22, card);
            player3.getBingoCard().creationCard();
            Player player4 = new Player(
                    "player4", "player4", "none", "none", 22, card);
            player4.getBingoCard().creationCard();
            Player player5 = new Player(
                    "player5", "player5", "none", "none", 22, card);
            player5.getBingoCard().creationCard();

            ////////optimizable
            game.outNumber(1000);

            //passing values to the GUI
            Controllers.backgroundTask(() ->
            {
                passerBtn.setText(GameFunction.arr[counter]);
                counter++;
            }, 1100);
            controls.numbersCardControl(player1);
            controls.numbersCardControl(player2);
            controls.numbersCardControl(player3);
            controls.numbersCardControl(player4);
            controls.numbersCardControl(player5);
        });

        //TO CHANGE USED JUST TO SEE HOW IT WAS WORKING
        Player player = new Player(
                "andrea", "skyires", "andrea.luiu@libero.it", "and", 22, card);
        player.getBingoCard().creationCard();

        cards(player.getBingoCard().getCard());

    }

    /**
     * This method create the card
     * for the player, inside the GUI
     * @param arr
     */
    @FXML
    public void cards(String arr[][]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                playerCard.getChildren().add(new Button(arr[i][j]));
            }
        }
    }

}
