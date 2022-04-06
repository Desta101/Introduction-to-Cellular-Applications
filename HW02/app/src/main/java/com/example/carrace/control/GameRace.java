package com.example.carrace.control;
/**
 * shimon Desta 203670286
 * HW01 - Car Game
 **/

import java.util.Arrays;
import java.util.Random;

public class GameRace {

    private int lives;
    private int[][] Arr;
    private int carPosition;
    private int speed;
    private boolean Playing;
   // private int score = 0;
    public GameRace() { }

    public void startGame(int rows, int cols, int livesNumber,int speedValue){
        lives = livesNumber;
        Arr = new int[rows][cols];
        carPosition = 2;
        speed = speedValue;
        Playing = true;
        Arr =makeMap(Arr);
        changeCarPosition(checkForCrach());
    }


    public int next(int counter) {
        int result = 0;
        if(Playing){
            generateNewPath(CheckIfRowIsClear(Arr[0]),counter);
            boolean crash =  checkForCrach(),hitCoin = checkForCoin();
            changeCarPosition(crash);
            if(crash){
                lives--;
                Gamecheck();
                result = 1;
            } else if(hitCoin){
                result = 3;
            }
        }else{
            result = 2;
        }
        return result;
    }

    private void Gamecheck() {
        if (lives == 0){
            Playing = false;
        }else{
            Playing = true;
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
    public int[][] makeMap(int arr[][]) {
        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j <arr[i].length ; j++) {
                arr[i][j] = 0;
            }
        }
        return arr;
    }
    private void generateNewPath(boolean withObstacles, int counter) {
        int rows = Arr.length;
        int cols = Arr[0].length;
        int[][]  tempArr;
        tempArr = new int[rows][cols];
        for (int i = rows - 1; i >=0; i--) {
                if(i==0){
                    for (int j = 0; j < cols; j++) { Arr[i][j] = 0;}
                    if(withObstacles) {
                        int testRandom = new Random().nextInt(cols);
                        Arr[i][testRandom] = 1;
                        if(counter%5==0){
                            int testCoin = new Random().nextInt(cols);
                            Arr[i][testCoin] = 4;
                        }
                    }
                }else {
                    Arr[i] = Arrays.copyOf(Arr[i-1],cols);
                }
        }
    }





    private void changeCarPosition(boolean crashed){
        if(crashed){
            Arr[Arr.length-1][carPosition] = 3;
        }else{
            Arr[Arr.length-1][carPosition] = 2;
        }
    }

    private boolean checkForCrach() {
        boolean result = false;
        if(Arr[Arr.length-1][carPosition]==1) result = true;
        return result;
    }

    public boolean isPlaying() {
        return Playing;
    }

    public GameRace setPlaying(boolean playing) {
        this.Playing = playing;
        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public GameRace setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public int getLives() {
        return lives;
    }

    public GameRace setLives(int lives) {
        this.lives = lives;
        return this;
    }

    public int[][] getArr() {
        return Arr;
    }

    public GameRace setArr(int[][] arr) {
        this.Arr = arr;
        return this;
    }

    public int getCarPosition() {
        return carPosition;
    }

    public GameRace setCarPosition(int carPosition) {
        this.carPosition = carPosition;
        return this;
    }
    private boolean checkForCoin() {
        boolean result = false;
        if(Arr[Arr.length-1][carPosition]==4)
            result = true;
        return result;
    }


    public void moveCar(int index){
        if(index == 1){ LeftM(); }
        else if (index == 0){ LeftR(); }
    }
    private void LeftM() {
        if(carPosition < Arr[Arr.length-1].length-1)
            carPosition++;
    }
    private void LeftR() {
        if(carPosition > 0)
            carPosition--;
    }

}
