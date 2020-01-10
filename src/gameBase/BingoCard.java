package gameBase;

import utilities.CardFunctionsController;

public class BingoCard {
    public static final int ROW = 3;
    public static final int CLM = 5;
    private String [][] card;
    private String [] controller = new String[15];

    public BingoCard(){
        card = new String [ROW][CLM];
    }

    /**
     * @return void
     * the creation card creates
     * "randomly" the card of a user
     * the cards do not contain duplicates
     */
    public String[][] creationCard(){
        CardFunctionsController.noDuplicates(15,controller);
        int s = 0;
        int control = (int)(Math.random()* 6) +1;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 5; j++) {
                card[i][j] = String.valueOf(Integer.parseInt(controller[s]) * control);
                s++;
            }
        }
        return card;
    }

    //getters and setter
    public String[][] getCard(){
        return card;
    }


}























