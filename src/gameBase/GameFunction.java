package gameBase;

import utilities.CardFunctionsController;

public class GameFunction {
    public static String arr[] = new String[90];

    /**
     * This method starts a thread
     * that insert values inside the
     * table,after every insertions
     * it will wait an amount of time
     * till it fills it.
     * @param sleepTime
     */
    public void outNumber(int sleepTime) {
        Thread thread = new Thread(() -> {
            int s = 0;
            CardFunctionsController.noDuplicates(90,arr);

            for(int i = 0; i < BingoTable.TABLE.length; i++) {
                for(int j = 0; j < BingoTable.TABLE[i].length; j++) {
                    BingoTable.TABLE[i][j] = arr[s];
                    s++;
                    try {Thread.sleep(sleepTime);} catch (InterruptedException e) {e.printStackTrace();}
                }
            }
        });
        thread.start();

    }
}
