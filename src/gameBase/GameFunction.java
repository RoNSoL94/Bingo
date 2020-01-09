package gameBase;

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
            noDuplicates(90);

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
    /**
     * This method allow to not
     * have duplicates inside the array
     * @param size
     * the size parameter is for the size of the array
     */
    private void noDuplicates(int size){
        int [] ar = new int [size];
        int count = 1;

        for(int i = 0; i < size; i++)  ar[i] = count++;
        count = 0;
        for(int i = 0; i < size; i ++) {
            int sm = ((int) (Math.random() * 90) + 1);
            for(int j = 0; j < size; j++) {
                if(ar[j] == sm) {
                    arr[i] = String.valueOf(sm);
                    ar[j] = 0;
                    count = 1;
                }
                if(j == size - 1 && count == 0) i --;
                if(j == size - 1)count = 0;
            }
        }
    }
}
