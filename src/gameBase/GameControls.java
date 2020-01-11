package gameBase;

public class GameControls {
    public static boolean ternoWon,quaternaWon,
            cinquinaWon = false,isRUnning = true;

    /**
     * This method check is one of the
     * players win something
     * @param value
     */
    public static void booleanControler(int value){
        if(value == 3)ternoWon = true;
        if(value == WinnerCheck.QUATERNA.getNumber())quaternaWon = true;
        if(value == WinnerCheck.CINQUINA.getNumber()) cinquinaWon = true;
    }

}