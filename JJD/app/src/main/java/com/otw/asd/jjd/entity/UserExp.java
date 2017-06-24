package com.otw.asd.jjd.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/23.
 */

public class UserExp implements Serializable{

    /**
     * currentLevel : 1
     * currentExp : 0
     * nextLevelExp : 5
     */

    private int currentLevel;
    private int currentExp;
    private int nextLevelExp;

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }
}
