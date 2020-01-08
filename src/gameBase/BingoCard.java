package gameBase;

public class BingoCard {
    public static final int ROW = 3;
    public static final int CLM = 5;
    private String [][] card;

    public BingoCard(){
        card = new String [ROW][CLM];
    }

    /**
     * @return void
     * the creation card creates
     * randomly the card of a user
     */
    public String[][] creationCard(){
        for(int i = 0; i < card.length; i++) {
            for(int j = 0; j < card[i].length; j++)
                card[i][j] = String.valueOf((int)(Math.random()*90) + 1);
        }
        return card;
    }

    /**
     * this method only print
     * the card passed in
     */
    public void printingCard(){
        for(int i = 0; i < card.length; i++) {
            for(int j = 0; j < card[i].length; j++)
                System.out.print(card[i][j] + "\t");
            System.out.println();
        }
    }

    public String[][] getCard(){
        return card;
    }
}























