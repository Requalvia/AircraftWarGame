package edu.hitsz.DAO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameRecord {
    private Date date;
    private String player;
    private int score;
    private int id;
    public GameRecord(Date date,String player,int score,int id){
        this.date=date;
        this.player=player;
        this.score=score;
        this.id=id;
    }

    public Date getDate() {return date;}

    public int getScore() {return score;}

    public String getPlayer() {return player;}

    public int getId() {return id;}

    public void setDate(Date date) {this.date = date;}

    public void setPlayer(String player) {this.player = player;}

    public void setScore(int score) {this.score = score;}

    public void setId(int id) {this.id = id;}
}
