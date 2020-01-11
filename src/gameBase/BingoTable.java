package gameBase;

import home.controller.InsiderController;
import utilities.CardFunctionsController;
import java.util.Arrays;

public class BingoTable {
    public static final int COL = 10;
    public static final int ROWS = 9;
    public static final String TABLE[][] = new String[ROWS][COL];
    public static String arr[] = new String[90];

    /**
     * This method starts a thread
     * that insert values inside the
     * table,after every insertions
     * it will wait an amount of time
     * till it fills it.
     *
     * @param sleepTime
     */
    public void outNumberThread(int sleepTime) {
        Thread thread = new Thread(() -> {
            outNumber(sleepTime);
        });
        thread.start();
    }

    private synchronized void outNumber(int sleepTime) {
        int s = 0;
        CardFunctionsController.noDuplicates(90, arr);

        for (int i = 0; i < TABLE.length; i++) {
            for (int j = 0; j < TABLE[i].length; j++) {
                TABLE[i][j] = arr[s];
                s++;
                if (InsiderController.waiting) {
                    try {
                        System.out.println("in wait position");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //to restart the game when its ended
        for (int i = 0; i < TABLE.length; i++)
            Arrays.fill(TABLE[i], null);
        for (int i = 0; i < TABLE.length; i++)
            BingoTable.arr[i] = null;
    }
}
