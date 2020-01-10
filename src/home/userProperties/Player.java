package home.userProperties;

import gameBase.BingoCard;

//Pojo class
public class Player {

    private String name;
    private String nickName;
    private String email;
    private String password;
    private int age;
    private BingoCard bingoCard;


    public Player(String name, String nickName, String email, String password, int age,BingoCard bingoCard) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.bingoCard = bingoCard;
    }

    //generally used to create some AI player
    public Player(String nickName){
        this.nickName = nickName;
        bingoCard = new BingoCard();
//        bingoCard.creationCard();
          bingoCard.creationCard();
    }

    public Player(){/*DEFAULT*/}


    //getter and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BingoCard getBingoCard() {
        return bingoCard;
    }

    public void setBingoCard(BingoCard bingoCard) {
        this.bingoCard = bingoCard;
    }
}
