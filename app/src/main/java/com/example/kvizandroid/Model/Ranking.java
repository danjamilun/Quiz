package com.example.kvizandroid.Model;

public class Ranking {

    private long score;
    private String userName;

    public Ranking() {
    }


    public Ranking(long score, String userName) {
        this.score = score;
        this.userName = userName;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
