package com.example.apurp_000.dementiaapp;

/**
 * Created by apurpura on 7/16/2015.
 */
public class ActivityResult {
    public String startTime;
    public String endTime;
    public String cancelTime;
    public String level;
    public String score;
    public String action;
    public String eventId;

    public ActivityResult(String StartTime, String EndTime, String CancelTime, String Level, String Score, String Action, String EventId){
        this.startTime = StartTime;
        this.endTime = EndTime;
        this.cancelTime = CancelTime;
        this.level = Level;
        this.score = Score;
        this.action = Action;
        this.eventId = EventId;
    }

}
