package com.example.kvizandroid.Model;

public class Score {

    private String score;
    private String user;

    public Score() {
    }

    public Score(String score, String user) {
        this.score = score;
        this.user = user;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
