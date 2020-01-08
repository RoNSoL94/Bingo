package gameBase;

public class BingoTable {

    public static final int COL = 10;
    public static final int ROWS = 9;
    public static final String TABLE[][] = new String[ROWS][COL];

    /**
     * @return void
     * this method is used to
     * print the table game
     */
    public static void printTableCard() {
        for (int i = 0; i < BingoTable.TABLE.length; i++) {
            for (int j = 0; j < BingoTable.TABLE[i].length; j++) {
                System.out.print(BingoTable.TABLE[i][j] + "\t");
            }
                System.out.println();
            }
        }
    }

