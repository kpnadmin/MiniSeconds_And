package seveno.android.miniseconds.SpeedNumGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Administrator on 2017-08-02.
 */

public class Sequence {
   // private final ArrayList<Integer> primeNumbers = new ArrayList<Integer>(Arrays.asList(2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,229));
   private final ArrayList<Integer> primeNumbers  = new ArrayList<Integer>(Arrays.asList(2,3,5,8, 12 , 17, 23, 30, 38));
    private ArrayList<Integer> randomedNumbers;
    private ArrayList<Integer> orderedNumbers;
    private int numCorrect;
    private int gameType =0;

    //Starts a new sequence determined by the game type.
    public Sequence(int gameType){
        this.gameType = gameType;
        numCorrect = 0;
        orderedNumbers = new ArrayList<Integer>(9);
        if(gameType==0){//Game Type: Singles
            for(int i = 1; i<=9; i++){
                orderedNumbers.add(i);
            }
        } else if(gameType == 1){//Game Type: Multiples
            Random rand1 = new Random();
            int r1  = rand1.nextInt(9)+1;
            for(int i = 1; i <= 9; i++){
                orderedNumbers.add(i*(r1));
            }
        } else if(gameType == 2){//Game Type: Odds
            for(int i = 0; i < 9; i++){
                orderedNumbers.add(1+(i*2));
            }
        } else if(gameType == 3){//Game Type: Primes
            orderedNumbers.addAll(primeNumbers);
        }
        orderedNumbers.trimToSize();
        randomedNumbers = new ArrayList<Integer>(orderedNumbers);
        shuffle();
    }

    //Randomizes the sequence in randomedNumbers.
    private void shuffle(){
        Random random = new Random();
        int n = randomedNumbers.size();
        while(n>1){
            int k = random.nextInt(n);
            n--;
            int temp = randomedNumbers.get(n);
            randomedNumbers.set(n,randomedNumbers.get(k));
            randomedNumbers.set(k, temp);
        }
    }

    //Returns a number from randomedNumbers to display on a button.
    public int getIntegerAt(int position){
        return randomedNumbers.get(position);
    }

    public boolean isDone(int toCheck) {
        boolean isDone = false;
        int index = orderedNumbers.indexOf(toCheck);
        if(index>=0 && index<numCorrect){
            isDone=true;
        }
        return isDone;

    }
    //Called when a button is pressed on the Play screen, checks if it is the next correct number in the sequence.
    public boolean isCorrect(int toCheck){
        boolean isCorrect = false;
        if(toCheck==orderedNumbers.get(numCorrect)){
            isCorrect = true;
            numCorrect++;
        }
        return isCorrect;
    }
    //Checks if all of the buttons have been pressed correctly. Is called each time a new button is found to be correct in Play.class.
    public boolean allCorrect(){
        boolean allCorrect = false;
        if(numCorrect==orderedNumbers.size()){
            allCorrect = true;
        }
        return allCorrect;
    }


    //Returns the string form of the game type.
    public String getgameType(){
        String modeName = "";
        switch(gameType){
            case 0: modeName = "Singles";
                break;
            case 1: modeName = "Multiples of n";
                break;
            case 2: modeName = "Odds";
                break;
            case 3: modeName = "Primes";
                break;
        }
        return modeName;
    }




}
