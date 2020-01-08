package gameBase;

public enum WinnerCheck {
    TERNO(3),QUATERNA(4),CINQUINA(5),TOMBOLA(15);

    int number;

     WinnerCheck(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
