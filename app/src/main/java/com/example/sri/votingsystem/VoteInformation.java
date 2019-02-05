package com.example.sri.votingsystem;

/**
 * Created by Sri on 4/9/2017.
 */

public class VoteInformation {

    public String QuesName;
    public int votes;
    public VoteInformation(){

    }

    public VoteInformation(String name, int vote) {
        this.QuesName = name;
        this.votes = vote;
    }

}
