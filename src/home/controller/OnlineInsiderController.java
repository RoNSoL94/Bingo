package home.controller;

import gameBase.BingoCard;
import gameBase.GameControls;
import gameBase.BingoTable;
import gameBase.WinnerCheck;
import home.userProperties.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Ellipse;
import net.Client;
import net.ClientConnections;
import utilities.CardFunctionsController;
import utilities.SceneController;
import utilities.TaskController;

public class OnlineInsiderController extends InsiderOperations implements CardFunctionsController<Button, Player> {

    @FXML
    private AnchorPane insideBackground;
//    @FXML
//    private VBox gameHistory;
//
//    @FXML
//    private Button exitBtn, ternoBtn,
//            quaternaBtn, cinquinaBtn, passerBtn,
//            tombolaBtn, closeSystem, changeCardBtn, chatBtn;
    @FXML
    private Button chatBtn;
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField chatTxt;
    @FXML
    public Label starterLbl;
    @FXML
    private GridPane playerCard;
    @FXML
    Ellipse shape;

    private static int counter = 0;
    private Button[][] button = new Button[3][5];
    public static boolean waiting = false;
    static boolean stabilizer = false;
    public static Client client;

    @FXML
    void initialize() {
        client = new Client();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (ClientConnections.maxConnections == null) {
            SceneController.changeScene(this, "/fxmlFiles/mainFrame.fxml");
            //to check
            insideBackground.getScene().getWindow().hide();
        } else {
            initializer();
            starting();

            //change scene
                exitBtn.setOnAction(ev -> {
                    exitBtn.getScene().getWindow().hide();
                    //path to update
                    GameControls.isRUnning = false;
                    SceneController.changeScene(this, "/fxmlFiles/mainFrame.fxml");
                });


                Player player = new Player(MainFrameController.prova);
                chooseCard(player);
                // if clicked change the card of the player
                changeCardBtn.setOnAction((v) -> {
                    chooseCard(player);
                    System.out.println(MainFrameController.prova);
                    changeCardBtn.setText("Change Card");
                });

                chatBtn.setOnAction((eve) -> {
                    if (!chatTxt.getText().equals(""))
                        client.writeChat(player.getNickName() + ": " + chatTxt.getText() + "\n\n");
                    chatTxt.setText("");
                });

                TaskController.backgroundTask(() -> cardControl(button), 400);

                TaskController.backgroundTask(() -> {
                    if (!ClientConnections.wins.equals("")) {
                        setGameHistoryOnline(ClientConnections.wins);
                        ClientConnections.wins = "";
                    }
                }, 400);

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


                //checking the buttonCardToBeClicked
                TaskController.backgroundTask(() -> {
                    int cnt = 0;
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (button[i][j].isPressed()) button[i][j].setDisable(true);
                        }
                    }
                }, 800);


                TaskController.backgroundTask(() -> {
                    if (ClientConnections.ctr == 1) {
                        chatArea.appendText(ClientConnections.chat + "\n");
                        ClientConnections.ctr = 0;
                    }
                },200);


                //leaving the rewards once won
                TaskController.backgroundTask(() -> {
                    if (GameControls.ternoWon) ternoBtn.setVisible(false);
                    if (GameControls.quaternaWon) quaternaBtn.setVisible(false);
                    if (GameControls.cinquinaWon) cinquinaBtn.setVisible(false);

                }, 5500);

                new BingoTable().outNumberThreadOnline(5540);

                //passing values to the GUI

                TaskController.backgroundTask(() ->
                {
                    if(BingoTable.notice) {
                        starterLbl.setText("START");
                        passerBtn.setText(BingoTable.arr[counter]);
                        System.out.println("counter" + counter);
                        counter++;
                        if(counter == 1) {
                            starterLbl.setVisible(false);
                            shape.setVisible(false);
                        }
                    }
                }, 5550);
            }
        }



    /**
     * This method create the card
     * for the player, inside the GUI
     *
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
                if (!GameControls.ternoWon)client.sendToServer("System: TERNO GOT BY " + player.getNickName());
                break;
            case 4:
                if (!GameControls.quaternaWon) client.sendToServer("System: QUATERNA GOT BY " + player.getNickName());
                break;
            case 5:
                if (!GameControls.cinquinaWon) client.sendToServer("System: CINQUINA GOT BY " + player.getNickName());
                break;
            case 15:
                client.sendToServer("System: TOMBOLA GOT BY " + player.getNickName());
                restart();
        }
    }

    /**
     * The method checks all the
     * wins and store them and
     * prints them on the screen
     *
     * @param what
     */
    private void setGameHistoryOnline(String what) {
        Label label = new Label(what);
        label.setPrefSize(300, 15);
        label.setStyle("-fx-text-fill: aliceblue;");
        gameHistory.getChildren().add(label);
        closeSystem.setVisible(true);
        gameHistory.setVisible(true);
        if (label.getText().substring(0, 13).equals("System: TERNO")) GameControls.ternoWon = true;
        if (label.getText().substring(0, 16).equals("System: QUATERNA")) GameControls.quaternaWon = true;
        if (label.getText().substring(0, 16).equals("System: CINQUINA")) GameControls.cinquinaWon = true;
        if(label.getText().substring(0,15).equals("System: TOMBOLA")) {
            changeCardBtn.setVisible(true);
            gameHistory.getChildren().clear();
            GameControls.ternoWon = true;
            GameControls.quaternaWon = true;
            GameControls.cinquinaWon = true;
            waiting = true;
            stabilizer = true;
            GameControls.isRUnning = false;
            passerBtn.setVisible(false);
            exitBtn.setVisible(true);
        }
    }

    /**
     * Allow palyers to choose
     * or change the card
     *
     * @param player
     */
    private void chooseCard(Player player) {
        player.getBingoCard().creationCard();
        cards(player.getBingoCard().getCard());
    }

}


