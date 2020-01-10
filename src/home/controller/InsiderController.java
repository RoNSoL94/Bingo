package home.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gameBase.BingoCard;
import gameBase.BingoTable;
import gameBase.GameControls;
import gameBase.GameFunction;
import home.userProperties.Player;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import utilities.CardFunctionsController;
import utilities.SceneController;
import utilities.TaskController;

public class InsiderController implements CardFunctionsController<Button, Player> {

    @FXML
    private AnchorPane insideBackground;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitBtn, ternoBtn, startBtn,
            quaternaBtn, cinquinaBtn, passerBtn, tombolaBtn;


    @FXML
    public  Label winnerLabel;

    @FXML
    private GridPane playerCard;
    //TO CHANGE USED JUST SEE
    private BingoCard card = new BingoCard();
    GameControls controls = new GameControls();
    GameFunction game = new GameFunction();
    private static int counter = 0;

    private Button[][] button = new Button[3][5];

    @FXML
    void initialize() {
        ternoBtn.setDisable(true);
        quaternaBtn.setDisable(true);
        cinquinaBtn.setDisable(true);
        tombolaBtn.setDisable(true);

        //this snippet needs to be changed
        Player player = new Player(
                "andrea", "skyires", "andrea.luiu@libero.it", "and", 22, card);
        player.getBingoCard().creationCard();
        cards(player.getBingoCard().getCard());

        passerBtn.setVisible(false);

        //change scene
        exitBtn.setOnAction(ev -> {
            exitBtn.getScene().getWindow().hide();
            //path to update
            SceneController.changeScene(this, "/fxmlFiles/mainFrame.fxml");
        });

        ternoBtn.setOnAction(ev -> controls.controllingNumber(player, BingoTable.TABLE));
        quaternaBtn.setOnAction(ev -> controls.controllingNumber(player, BingoTable.TABLE));
        cinquinaBtn.setOnAction(ev -> controls.controllingNumber(player, BingoTable.TABLE));
        tombolaBtn.setOnAction(ev -> controls.controllingNumber(player, BingoTable.TABLE));
        
        //starts the game
        startBtn.setOnAction(ev -> {
            startBtn.setVisible(false);
            passerBtn.setVisible(true);
            TaskController.backgroundTask(() -> {
                int cnt = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (button[i][j].isPressed()) button[i][j].setDisable(true);
                    }
                }
            }, 1000);

            TaskController.backgroundTask(() -> {
                controllingNumber(player, button);
            }, 200);

            //leaving the rewards once won
            TaskController.backgroundTask(() -> {
                if (GameControls.ternoWon) ternoBtn.setVisible(false);
                if (GameControls.quaternaWon) quaternaBtn.setVisible(false);
                if (GameControls.cinquinaWon) cinquinaBtn.setVisible(false);
            }, 2000);


//            //creates the AI players
            for (int i = 0; i < 5; i++) {
                controls.numbersCardControl(new Player("player" + i));
            }

            game.outNumber(500);

            //passing values to the GUI
            TaskController.backgroundTask(() ->
            {
                passerBtn.setText(GameFunction.arr[counter]);
                counter++;
            }, 450);
        });
    }

    /**
     * This method create the card
     * for the player, inside the GUI
     * @param arr
     */
    public void cards(String arr[][]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                button[i][j] = new Button(arr[i][j]);
                button[i][j].setPrefSize(40, 40);
                button[i][j].setStyle("-fx-background-color: grey;\n" +
                        "    -fx-border-width: 0.3;\n" +
                        "    -fx-border-color: darkgray;");
                playerCard.add(button[i][j], j, i);
            }
        }
    }

    private int hideBtnControl(int value) {
        switch (value) {
            case 3:
                ternoBtn.setDisable(false);
                break;
            case 4:
                quaternaBtn.setDisable(false);
                break;
            case 5:
                cinquinaBtn.setDisable(false);
                break;
            case 15:
                tombolaBtn.setDisable(false);
        }
        return value;
    }

    @Override
    public void controllingNumber(Player player, Button[][] btn) {
            int count = 0, countOne = 0, countTwo = 0, tombola;

            for (int j = 0; j < 5; j++) {
                if (btn[0][j].isDisabled()) {
                    count++;
                    hideBtnControl(count);
                }
                if (btn[1][j].isDisabled()) {
                    countOne++;
                    hideBtnControl(countOne);
                }
                if (btn[2][j].isDisabled()) {
                    countTwo++;
                    hideBtnControl(countTwo);
                }
                tombola = (count + countOne + countTwo);
                if(tombola == 15)
                    hideBtnControl(tombola); }
            }

}
