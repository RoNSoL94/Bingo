package gameBase;
import home.userProperties.Player;

public class GameControls {
    public static boolean ternoWon,quaternaWon,cinquinaWon = false;

    /**
     * This method check if a player win something
     * if the player win the reward it will no
     * longer be accessible
     * @param value
     * @param player
     */
    private synchronized static void controller(int value, Player player) {
        switch (value) {
            case 3:
                if(!ternoWon)
                System.out.println("terno " + player.getName());
                //TODO
                break;
            case 4:
                if(!quaternaWon)
                System.out.println("quaterna "+ player.getName());
                //TODO
                break;
            case 5:
                if(!cinquinaWon)
                System.out.println("cinquina "+ player.getName());
                //TODO
                break;
            case 15:
                System.out.println("tombola "+ player.getName());
                /*then take back the player to the start screen*/
                //TODO
                System.exit(0);
                break;
        }
    }

    /**
     * This method create a thread that run
     * in max priority, updating the "cards"
     * everytime a AI player win something
     * @param player
     */
    public void numbersCardControl(Player player) {
        Thread thread = new Thread(() -> {
            while (true)
            controllingNumber(BingoTable.TABLE,player);
        });
        thread.start();
        thread.setPriority(Thread.MAX_PRIORITY);
    }
    /**
     * this method checks and updates
     * if a player wins
     * @param card
     * @param player
     */
    private void controllingNumber(String [][] card,Player player){
        String tmp [] = new String[90];
        int count = 0,countOne = 0,countTwo = 0,tombola = 0;


        for(int i = 0; i < BingoTable.ROWS; i++) {
            for(int j = 0; j < BingoTable.COL; j++) {
                tmp[count++] = card[i][j];/*card is the BingoTable*/
            }
        }
        count = 0;
        for(int i = 0; i < 90; i++) {
            for(int j = 0; j < BingoCard.CLM; j++) {
                if(player.getBingoCard().getCard()[0][j].equals(tmp[i]) && count < 5){
                    count++;
                    controller(count,player);
                    booleanControler(count);
                }else if(player.getBingoCard().getCard()[1][j].equals(tmp[i])&& countOne < 5) {
                    countOne++;
                    controller(countOne,player);
                    booleanControler(countOne);
                }else if(player.getBingoCard().getCard()[2][j].equals(tmp[i])&& countTwo < 5){
                    countTwo++;
                    controller(countTwo,player);
                    booleanControler(countTwo);
                }
                tombola = (count + countOne + countTwo);
                if(tombola == 15) controller(tombola,player);
            }
        }
    }
    /**
     * This method check is one of the
     * players win something
     * @param value
     */
    private static void booleanControler(int value){
        if(value == 3)ternoWon = true;
        if(value == 4)quaternaWon = true;
        if(value == 5) cinquinaWon = true;
    }

}