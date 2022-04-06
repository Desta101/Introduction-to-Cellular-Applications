package com.example.carrace;
/**
  * shimon Desta 203670286
  * HW01 - Car Game
  **/


import android.content.Intent;
import android.widget.TextView;

import com.example.carrace.Activities.MainActivity;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;

public class CarGame {

    private int lives;
    private int[][] arr;
    private int carPosition;
    private int speed;
    private boolean gameIsOn;
    private int score = 0;
    public CarGame() {

    }



    public void startGame(int rows, int cols, int livesNumber,int speedValue){
        lives = livesNumber;
        arr = new int[rows][cols];
        speed = speedValue;
        carPosition = 1;
        gameIsOn = true;

        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j <arr[i].length ; j++) {
                arr[i][j] = 0;
            }
        }
        // set car
    }


    public int next() {
        int result = 0; // gameIsOn = 0, crash = 1, gameIsOf = 2
        if(gameIsOn){
            generateNewPath(CheckIfRowIsClear(arr[0]));
            boolean crash =  checkForCrach();
            changeCarPosition(crash);
            if(crash){
                lives--;
                checkforGamestatus();
                result = 1;
            }
        }else{
            result = 2;
        }

        return result;
    }


    private void checkforGamestatus() {
        if (lives == 0){
            gameIsOn = false;
        }else{
            gameIsOn = true;
        }
    }

    private boolean CheckIfRowIsClear(int[] row){
        boolean result = true;
        for (int i = 0; i < row.length; i++) {
            if(row[i]!=0){
                result = false;
            }
        }
        return result;
    }
    private void generateNewPath(boolean withObstacles) {
        int rows = arr.length;
        int cols = arr[0].length;
        int[][] tempVals = new int[rows][cols];
        for (int i = rows - 1; i >=0; i--) {
                //handel first row
                if(i==0){
                    //generate clean path
                    for (int j = 0; j < cols; j++) {
                        arr[i][j] = 0;
                    }
                    // insert obstacle if needed
                    if(withObstacles) {
                        int random = new Random().nextInt(cols);
                        arr[i][random] = 1;
                    }

                }else {
                    //copy the above row
                    arr[i] = Arrays.copyOf(arr[i-1],cols);
                }
        }

    }

    private boolean checkForCrach() {
        boolean result = false;
        if(arr[3][carPosition]==1)
            result = true;
        return result;
    }


    public void moveCar(int index){
        if(index == 1){
            moveleft();
        }else if (index == 0){
            moveRight();
        }
    }

    private void moveRight() {
        if(carPosition > 0)
            carPosition--;
    }

    private void moveleft() {
        if(carPosition < arr[arr.length-1].length-1)
            carPosition++;
    }
    private void changeCarPosition(boolean crashed){
        if(crashed){
            arr[arr.length-1][carPosition] = 3;
        }else{
            arr[arr.length-1][carPosition] = 2;
        }
    }

    // getters and setters

    public boolean isGameIsOn() {
        return gameIsOn;
    }

    public CarGame setGameIsOn(boolean gameIsOn) {
        this.gameIsOn = gameIsOn;
        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public CarGame setSpeed(int speed) {
        this.speed = speed;
        return this;
    }
    public int getScore() {
        return score;
    }

    public CarGame setScore(int score) {
        this.score = score;
        return this;
    }

    public int getLives() {
        return lives;
    }

    public CarGame setLives(int lives) {
        this.lives = lives;
        return this;
    }

    public int[][] getVals() {
        return arr;
    }

    public CarGame setVals(int[][] arr) {
        this.arr = arr;
        return this;
    }

    public int getCarPosition() {
        return carPosition;
    }

    public CarGame setCarPosition(int carPosition) {
        this.carPosition = carPosition;
        return this;
    }


}
