package gameBase;

import home.controller.OnlineInsiderController;
import net.ClientConnections;
import utilities.CardFunctionsController;

import java.util.Arrays;

public class BingoTable {
    public static final int COL = 10;
    public static final int ROWS = 9;
    public static final String TABLE[][] = new String[ROWS][COL];
    public static String arr[] = new String[90];
    public static boolean notice = false;

    /**
     * This method starts a thread
     * that insert values inside the
     * table,after every insertions
     * it will wait an amount of time
     * till it fills it.
     *
     * @param sleepTime
     */
    public void outNumberThreadOnline(int sleepTime) {
        Thread thread = new Thread(() -> {
            outNumberOnline(sleepTime);
        });
        thread.start();
    }

    private synchronized void outNumberOnline(int sleepTime) {
        int s = 0;
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("control: " + GameControls.isRUnning);
        for (int i = 0; i < 90; i++) {
            arr[i] = ClientConnections.array[i];
            System.out.print("table: " + arr[i] + " ");
        }

        notice = true;
        internalOperation(sleepTime, s);
    }

    public void outNumberThread(int sleepTime) {
        Thread thread = new Thread(() -> {
            outNumber(sleepTime);
        });
        thread.start();
    }

    private synchronized void outNumber(int sleepTime) {
        int s = 0;
        CardFunctionsController.noDuplicates(90, arr);
        internalOperation(sleepTime, s);
    }

    private void internalOperation(int sleepTime, int counter) {
        for (int i = 0; i < TABLE.length; i++) {
            for (int j = 0; j < TABLE[i].length; j++) {
                TABLE[i][j] = arr[counter];
                counter++;
                if (OnlineInsiderController.waiting) {
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
