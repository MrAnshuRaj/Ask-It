package com.anshu.askit.models;

public class Question {
    public String question;
    public String answer;
    public String tags;
    public int upVotes, downVotes;
    public boolean upClick=false,downClick = false;
    public long uniqueID;
    public Question(String question, String answer, String tags,int upVotes, int downVotes,long uniqueID)
    {
        this.answer = answer;
        this.tags = tags;
        this.question = question;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.uniqueID = uniqueID;
    }
    public Question()
    {

    }

}
