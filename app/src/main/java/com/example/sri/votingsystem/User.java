package com.example.sri.votingsystem;

/**
 * Created by Sri on 4/12/2017.
 */

public class User{
    public int Age;
    public String Name;
    //public boolean bool;
    public String bool;
    //public int ques;



    public User() {
    }

    //public User(int age, String name, boolean bool, int ques) {
    public User(int age,String name,String bool){
        Age = age;
        Name = name;
        this.bool = bool;
        //this.ques = ques;
    }
}