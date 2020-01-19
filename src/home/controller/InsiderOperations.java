package home.controller;

import gameBase.GameControls;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public abstract class InsiderOperations {

    @FXML
    protected AnchorPane insideBackground;
    @FXML
    protected VBox gameHistory;
    @FXML
    protected Button exitBtn, ternoBtn, startBtn,
            quaternaBtn, cinquinaBtn, passerBtn,
            tombolaBtn, pauseBtn,closeSystem,changeCardBtn;
    @FXML
    protected Label winnerLabel;
    @FXML
    protected GridPane playerCard;

    protected static int counter = 0;
    protected Button[][] button = new Button[3][5];
    public static boolean waiting = false;
    static boolean stabilizer = false;

    /**
     * Makes the wins button visible
     * dependent if they have enough number
     * to do that
     * @param value
     * @return
     */
    public int hideBtnControl(int value) {
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

    /**
     * The method checks all the
     * wins and store them and
     * prints them on the screen
     * @param txt
     */
    public void gameHistoryController(String txt){
        Label label = new Label(txt);
        label.setPrefSize(250,15);
        label.setStyle("-fx-text-fill: aliceblue;");
        gameHistory.getChildren().add(label);
        closeSystem.setVisible(true);
        gameHistory.setVisible(true);
    }

    /**
     * Takes the component
     * and controllers to
     * the default state
     */
    public void restart() {
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
    public void starting(){
        changeCardBtn.setVisible(false);
        gameHistory.getChildren().clear();
        GameControls.ternoWon = false;
        GameControls.quaternaWon = false;
        GameControls.cinquinaWon = false;
        waiting = false;
        stabilizer = false;
        GameControls.isRUnning = true;
        passerBtn.setVisible(true);
        exitBtn.setVisible(false);
    }

    public void cardControl(Button[][] btn) {
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
    }

    public void initializer(){
        gameHistory.setVisible(false);
        closeSystem.setVisible(false);
        ternoBtn.setDisable(true);
        quaternaBtn.setDisable(true);
        cinquinaBtn.setDisable(true);
        tombolaBtn.setDisable(true);
        passerBtn.setVisible(false);
    }
}
