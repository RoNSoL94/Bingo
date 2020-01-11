package home.userProperties;

import gameBase.BingoCard;

//Pojo class
public class Player {

    private String nickName;
    private BingoCard bingoCard;


    public Player(String nickName,BingoCard bingoCard) {
        this.nickName = nickName;
        this.bingoCard = bingoCard;
    }

    //generally used to create some AI player
    public Player(String nickName){
        this.nickName = nickName;
        bingoCard = new BingoCard();
        bingoCard.creationCard();
    }

    public Player(){/*DEFAULT*/}

    //getter and setters
    public String getNickName() {
        return nickName;
    }
    public BingoCard getBingoCard() {
        return bingoCard;
    }

}
