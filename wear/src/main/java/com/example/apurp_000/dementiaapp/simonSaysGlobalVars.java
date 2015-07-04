package com.example.apurp_000.dementiaapp;

/**
 * Created by DavidI on 7/3/2015.
 */
public class simonSaysGlobalVars{
    private static simonSaysGlobalVars instance;

    // Global variable
    private int level;
    private int levelHighest;
    private int streak;
    private int tWins;
    private int tLost;

    // Restrict the constructor from being instantiated
    private simonSaysGlobalVars(){}

    public void setLevelData(int hL){
        this.level=hL;
    }
    public int getLevelData(){
        return this.level;
    }
    public void setStreakData(int hS){
        this.streak=hS;
    }
    public int getStreakData(){
        return this.levelHighest;
    }
    public void setlevelHighestData(int hHL){
        this.levelHighest=hHL;
    }
    public int getlevelHighestData(){
        return this.streak;
    }
    public void setToalWinsData(int hTW){
        this.tWins=hTW;
    }
    public int getTotalWinsData(){
        return this.tWins;
    }
    public void setToalLostData(int hTL){
        this.tLost=hTL;
    }
    public int getTotalLostData(){
        return this.tLost;
    }


    public static synchronized simonSaysGlobalVars getInstance(){
        if(instance==null){
            instance=new simonSaysGlobalVars();
        }
        return instance;
    }
}
