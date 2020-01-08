package gameBase;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameFunction {

    /**
     * @return void
     * the method create a thread
     * which controls if the table
     * has number left and close a
     * box in the table
     */
    public void outNumber() {
        Thread thread = new Thread(() -> {
            HashSet<String> hash = new HashSet<>(90);

            while(hash.size() < 90){
                String tmps = String.valueOf((int)(Math.random()* 90) + 1);
                hash.add(tmps);
            }
            int s = 0;
            List<String> ar = new ArrayList<>();
            ar.addAll(hash);


            for(int i = 0; i < BingoTable.TABLE.length; i++) {
                for(int j = 0; j < BingoTable.TABLE[i].length; j++) {
                    BingoTable.TABLE[i][j] = ar.get(s);
                    s++;
                    System.out.print(BingoTable.TABLE[i][j] + "\t");
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
            }
        });
        thread.start();

    }

}
