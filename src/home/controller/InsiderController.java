package home.controller;

import java.net.URL;
import java.util.ResourceBundle;

import gameBase.BingoCard;
import gameBase.GameControls;
import gameBase.BingoTable;
import gameBase.WinnerCheck;
import home.userProperties.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import utilities.CardFunctionsController;
import utilities.SceneController;
import utilities.TaskController;

public class InsiderController implements CardFunctionsController<Button, Player> {

    @FXML
    private AnchorPane insideBackground;
    @FXML
    private VBox gameHistory;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button exitBtn, ternoBtn, startBtn,
            quaternaBtn, cinquinaBtn, passerBtn,
            tombolaBtn, pauseBtn,closeSystem,changeCardBtn;
    @FXML
    public Label winnerLabel;
    @FXML
    private GridPane playerCard;

    //TO CHANGE USED JUST SEE
    BingoTable game = new BingoTable();

    private static int counter = 0;
    private Button[][] button = new Button[3][5];
    public static boolean waiting = false;
    static boolean stabilizer = false;


    @FXML
    void initialize() {
        gameHistory.setVisible(false);
        closeSystem.setVisible(false);
        ternoBtn.setDisable(true);
        quaternaBtn.setDisable(true);
        cinquinaBtn.setDisable(true);
        tombolaBtn.setDisable(true);
        passerBtn.setVisible(false);

        //change scene
        exitBtn.setOnAction(ev -> {
            exitBtn.getScene().getWindow().hide();
            //path to update
            GameControls.isRUnning = false;
            SceneController.changeScene(this, "/fxmlFiles/mainFrame.fxml");
        });

        Player player = new Player(MainFrameController.prova, new BingoCard());

        // if clicked change the card of the player
        changeCardBtn.setOnAction((v) -> {
            chooseCard(player);
            System.out.println(MainFrameController.prova);
            changeCardBtn.setText("Change Card");
        });

        //starts the game
        startBtn.setOnAction(ev -> {
            if(!changeCardBtn.getText().equals("Choose Card")) {
                starting();
                for (int i = 0; i < 5; i++)
                    controllingNumber(new Player("PlayerBot_" + i), button);

                if (startBtn.getText().equals("Play Again")) {
                    player.getBingoCard().creationCard();
                    //creates the AI players
                    for (int i = 0; i < 5; i++)
                        controllingNumber(new Player("PlayerBot_" + i), button);
                } else if (startBtn.getText().equals("Resume")) GameControls.isRUnning = true;
                else if (startBtn.getText().equals("Play")) {
                    for (int i = 0; i < 5; i++)
                        controllingNumber(new Player("PlayerBot_" + i), button);
                }

                closeSystem.setOnAction((s) -> {
                    if (closeSystem.getText().equals("Close System")) {
                        closeSystem.setText("Show System");
                        gameHistory.setVisible(false);
                    } else {
                        closeSystem.setText("Close System");
                        gameHistory.setVisible(true);
                    }
                });
                //listener
                ternoBtn.setOnAction(eve -> controllingNumber(player, button));
                //listener
                quaternaBtn.setOnAction(eve -> controllingNumber(player, button));
                //listener
                cinquinaBtn.setOnAction(event -> controllingNumber(player, button));
                //listener
                tombolaBtn.setOnAction(events -> controllingNumber(player, button));
                //listener
                pauseBtn.setOnAction(events -> {
                    waiting = true;
                    GameControls.isRUnning = false;
                    startBtn.setText("Resume");
                    startBtn.setVisible(true);
                });

                //checking the buttonCardToBeClicked
                TaskController.backgroundTask(() -> {
                    int cnt = 0;
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (button[i][j].isPressed()) button[i][j].setDisable(true);
                        }
                    }
                }, 1200);

                //leaving the rewards once won
                TaskController.backgroundTask(() -> {
                    if (GameControls.ternoWon) ternoBtn.setVisible(false);
                    if (GameControls.quaternaWon) quaternaBtn.setVisible(false);
                    if (GameControls.cinquinaWon) cinquinaBtn.setVisible(false);
                }, 2000);

//                new BingoTable().outNumberThread(140);
                game.outNumberThread(9000);

                //passing values to the GUI
                TaskController.backgroundTask(() ->
                {
                    passerBtn.setText(BingoTable.arr[counter]);
                    System.out.println("counter" + counter);
                    counter++;
                }, 9100);
            }else System.out.println("error");
        });
    }

    /**
     * This method create the card
     * for the player, inside the GUI
     *
     * @param arr
     */
    public void cards(String arr[][]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                button[i][j] = new Button(arr[i][j]);
                button[i][j].setPrefSize(40, 40);
                button[i][j].setStyle("-fx-background-color: peru;\n" +
                        "    -fx-border-width: 0.3;\n" +
                        "    -fx-border-color: burlywood;");
                playerCard.add(button[i][j], j, i);
            }
        }
    }

    /**
     * Makes the wins button visible
     * dependent if they have enough number
     * to do that
     * @param value
     * @return
     */
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
        TaskController.backgroundTask(() -> {
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
                if (tombola == 15)
                    hideBtnControl(tombola);
            }

            String tmp[] = new String[90];
            count = 0;
            countOne = 0;
            countTwo = 0;
            for (int i = 0; i < BingoTable.ROWS; i++) {
                for (int j = 0; j < BingoTable.COL; j++) {
                    tmp[count++] = BingoTable.TABLE[i][j];/*card is the BingoTable*/
                }
            }
            count = 0;
            for (int i = 0; i < 90; i++) {
                for (int j = 0; j < BingoCard.CLM; j++) {
                    if (player.getBingoCard().getCard()[0][j].equals(tmp[i]) && count < 5) {
                        count++;
                        controller(count, player);
                        GameControls.booleanControler(count);
                    } else if (player.getBingoCard().getCard()[1][j].equals(tmp[i]) && countOne < 5) {
                        countOne++;
                        controller(countOne, player);
                        GameControls.booleanControler(countOne);

                    } else if (player.getBingoCard().getCard()[2][j].equals(tmp[i]) && countTwo < 5) {
                        countTwo++;
                        controller(countTwo, player);
                        GameControls.booleanControler(countTwo);
                    }
                    tombola = (count + countOne + countTwo);
                    if (tombola == WinnerCheck.TOMBOLA.getNumber() && !stabilizer) {
                        stabilizer = true;
                        controller(tombola, player);
                    }
                }
            }
        }, 300);
    }

    @Override
    public void controller(int value, Player player) {
        switch (value) {
            case 3:
                if (!GameControls.ternoWon) gameHistoryController("System: " + player.getNickName() + " GOT TERNO" );
                break;
            case 4:
                if (!GameControls.quaternaWon) gameHistoryController("System: " + player.getNickName() + " GOT QUATERNA");
                break;
            case 5:
                if (!GameControls.cinquinaWon) gameHistoryController("System: " + player.getNickName() + " GOT CINQUINA");
                break;
            case 15:
                gameHistoryController("System: " + player.getNickName() + " GOT !BINGO!");
                restart();
        }
    }

    /**
     * The method checks all the
     * wins and store them and
     * prints them on the screen
     * @param txt
     */
    private void gameHistoryController(String txt){
        Label label = new Label(txt);
        label.setPrefSize(250,15);
        label.setStyle("-fx-text-fill: aliceblue;");
        gameHistory.getChildren().add(label);
        closeSystem.setVisible(true);
        gameHistory.setVisible(true);
    }

    /**
     * Allow palyers to choose
     * or change the card
     * @param player
     */
    private void chooseCard(Player player){
        player.getBingoCard().creationCard();
        cards(player.getBingoCard().getCard());
    }

    /**
     * Takes the component
     * and controllers to
     * the default state
     */
    private void restart() {
        GameControls.isRUnning = false;
        ternoBtn.setVisible(true);
        quaternaBtn.setVisible(true);
        cinquinaBtn.setVisible(true);
        tombolaBtn.setVisible(true);
        pauseBtn.setVisible(false);
        exitBtn.setVisible(true);
        gameHistory.setVisible(false);
        counter = 0;
        startBtn.setText("Play Again");
        startBtn.setPrefSize(100,40);
        startBtn.setVisible(true);
    }

    /**
     * Takes the component
     * and controllers(boolean)
     * to the active state
     */
    private void starting(){
        changeCardBtn.setVisible(false);
        gameHistory.getChildren().clear();
        pauseBtn.setVisible(true);
        GameControls.ternoWon = false;
        GameControls.quaternaWon = false;
        GameControls.cinquinaWon = false;
        waiting = false;
        stabilizer = false;
        GameControls.isRUnning = true;
        startBtn.setVisible(false);
        passerBtn.setVisible(true);
        exitBtn.setVisible(false);
    }
}
