package gameBase;
import home.userProperties.Player;

public class GameControls {
    static boolean ternoWon,quaternaWon,cinquinaWon = false;

    /*the winner control*/
    private synchronized static void controller(int value,Player player) {
        switch (value) {
            case 3:
                if(!ternoWon)
                System.out.println("terno " + player.getName());
                break;
            case 4:
                if(!quaternaWon)
                System.out.println("quaterna "+ player.getName());
                break;
            case 5:
                if(!cinquinaWon)
                System.out.println("cinquina "+ player.getName());
                break;
            case 15:
                System.out.println("tombola "+ player.getName());
                /*then take back the player to the start screen*/
                System.exit(0);
                break;
        }
    }

    /*the number control*/
    public void numbersCardControl(Player player) {
        Thread thread = new Thread(() -> {
            while (true)
            controllingNumber(BingoTable.TABLE,player);
        });
        thread.start();
        thread.setPriority(Thread.MAX_PRIORITY);
    }



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

    private static void booleanControler(int value){
        if(value == 3)ternoWon = true;
        if(value == 4)quaternaWon = true;
        if(value == 5) cinquinaWon = true;
    }

    public static void main(String[] args) {
        GameFunction function = new GameFunction();
        function.outNumber();
        GameControls contrl = new GameControls();
        Player player = new Player(
                "andrea","luiu",
                "andrea.luiu@libero.it","spero",22,new BingoCard());
        Player player2 = new Player(
                "gino","spirolello",
                "andrea.luiu@libero.it","spero",22,new BingoCard());
        Player player3 = new Player(
                "francesca","luiu",
                "andrea.luiu@libero.it","spero",22,new BingoCard());
        Player player4 = new Player(
                "monica","spirolello",
                "andrea.luiu@libero.it","spero",22,new BingoCard());
        Player player5 = new Player(
                "silvano","luiu",
                "andrea.luiu@libero.it","spero",22,new BingoCard());
        Player player6 = new Player(
                "oscar","spirolello",
                "andrea.luiu@libero.it","spero",22,new BingoCard());

        player.getBingoCard().creationCard();
        player2.getBingoCard().creationCard();
        player3.getBingoCard().creationCard();
        player4.getBingoCard().creationCard();
        player5.getBingoCard().creationCard();
        player6.getBingoCard().creationCard();

        player.getBingoCard().printingCard();
        System.out.println("\n////////////////////////////////7\n");
        player2.getBingoCard().printingCard();
        System.out.println("\n////////////////////////////////7\n");
        player3.getBingoCard().printingCard();
        System.out.println("\n////////////////////////////////7\n");
        player4.getBingoCard().printingCard();
        System.out.println("\n////////////////////////////////7\n");
        player5.getBingoCard().printingCard();
        System.out.println("\n////////////////////////////////7\n");
        player6.getBingoCard().printingCard();
        System.out.println("\n////////////////////////////////7\n");


        contrl.numbersCardControl(player);
        contrl.numbersCardControl(player2);
        contrl.numbersCardControl(player3);
        contrl.numbersCardControl(player4);
        contrl.numbersCardControl(player5);
        contrl.numbersCardControl(player6);


    }
}