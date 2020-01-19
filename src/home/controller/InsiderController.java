package home.controller;

import gameBase.BingoCard;
import gameBase.GameControls;
import gameBase.BingoTable;
import gameBase.WinnerCheck;
import home.userProperties.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utilities.CardFunctionsController;
import utilities.SceneController;
import utilities.TaskController;

public class InsiderController extends InsiderOperations implements CardFunctionsController<Button, Player> {

    @FXML
    void initialize() {
          initializer();

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
                if (!changeCardBtn.getText().equals("Choose Card")) {
                    starting();
                    pauseBtn.setVisible(true);
                    startBtn.setVisible(false);

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
                    }, 700);

                    TaskController.backgroundTask(() -> cardControl(button), 400);

                    //leaving the rewards once won
                    TaskController.backgroundTask(() -> {
                        if (GameControls.ternoWon) ternoBtn.setVisible(false);
                        if (GameControls.quaternaWon) quaternaBtn.setVisible(false);
                        if (GameControls.cinquinaWon) cinquinaBtn.setVisible(false);
                    }, 450);

                    new BingoTable().outNumberThread(200);

                    //passing values to the GUI
                    TaskController.backgroundTask(() ->
                    {
                        passerBtn.setText(BingoTable.arr[counter]);
                        System.out.println("counter" + counter);
                        counter++;
                    }, 250);
                } else System.out.println("error");
            });
    }

    /**
     * This method create the card
     * for the player, inside the GUI
     * @param arr
     */
    public void cards(String arr[][]) {
        playerCard.getChildren().clear();
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

    @Override
    public void controllingNumber(Player player, Button[][] btn) {
        TaskController.backgroundTask(() -> {
            int count = 0, countOne = 0, countTwo = 0, tombola;


            String tmp[] = new String[90];
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
     * Allow palyers to choose
     * or change the card
     * @param player
     */
    private void chooseCard(Player player){
        player.getBingoCard().creationCard();
        cards(player.getBingoCard().getCard());
    }

}
