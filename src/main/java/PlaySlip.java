import java.util.*;

public class PlaySlip {
    private final Set<Integer> selectedSpots = new HashSet<Integer>();
    private final Set<Integer> random20Numbers = new HashSet<Integer>();
    private final ArrayList<Integer> theMatchedNums = new ArrayList<>();
    private int numSpots;
    private int numDrawings;
    private boolean drawsCompleted;
    private int currentDrawNum;
    private int drawWinningMoney;
    private int totalGameWinningMoney;

    /* Playslip constructor generates a playslip
     * object with given drawings and spots */
    public PlaySlip() {
        totalGameWinningMoney = 0;
    }

    /* Adds a number to the user's selected spot numbers */
    public void addSpotNum(int number) {
        selectedSpots.add(number);
    }

    /*Removes a number to the user's selected spot numbers */
    public void removeSpotNum(int number) {
        selectedSpots.remove(number);
    }

    /* New Round means that user continues playing game
     * but fills out new play slip. Hence, all values
     * except total winnings are reset
     * */
    public void newRound(int drawings, int spots) {
        drawsCompleted = false;
        currentDrawNum = 1;
        numSpots = spots;
        numDrawings = drawings;
        drawWinningMoney = 0;
        selectedSpots.clear();
        random20Numbers.clear();
        theMatchedNums.clear();
    }

    /* New Draw means that the user continues playing the game
     * with their existing selected numbers but with newly
     * generated winning numbers. Hence, matched and winning
     * numbers are reset. */
    public void newDraw() {
        if (!checkNumSpots() || drawsCompleted) {
            return;
        }
        if (currentDrawNum == numDrawings) {
            drawsCompleted = true;
        } else {
            currentDrawNum++;
        }
        drawWinningMoney = 0;
        random20Numbers.clear();
        theMatchedNums.clear();
        generate20RandNums();
        generateMatches();
        generateDrawWinningMoney(theMatchedNums.size());
    }

    /*
     * This method generates random numbers for the user. The
     * number of numbers selected will depend on the numSpots
     * they have selected */
    public void quickPick() {
        Random randNumber = new Random();
        selectedSpots.clear();
        while (selectedSpots.size() != numSpots) {
            int i = randNumber.nextInt(80) + 1;
            selectedSpots.add(i);
        }
    }

    /* This checks if the user has selected the right amount of spots
     * from the grid */
    public boolean checkNumSpots() {
        return selectedSpots.size() == numSpots;
    }

    /*Tell us whether the user has draws left and can continue playing*/
    public boolean checkDrawsCompleted() {
        return drawsCompleted;
    }

    /* This function is used in the UI. It returns a string of all the 20
     * random numbers*/
    public ArrayList<Integer> generate20RandNums() {
        Random randNumber = new Random();
        while (random20Numbers.size() != 20) {
            random20Numbers.add(randNumber.nextInt(80) + 1);
        }
        ArrayList<Integer> winningSpotsList = new ArrayList<>(random20Numbers);
        return winningSpotsList;
    }

    /* uses the selected spots and winning spots set to create a set and an array
     * of all the matches */
    public ArrayList<Integer> generateMatches() {
        // Iterate through the gameNums to find a matched number with the userNums. If so, append that number to the array
        for (Integer num : random20Numbers) {
            if (selectedSpots.contains(num)) {
                theMatchedNums.add(num);
            }
        }
        Collections.sort(theMatchedNums);  // Sort the matched numbers in ascending order
        return theMatchedNums;
    }

    /* This function calculates the total money earned for a specific draw
     * and also updates the total money earned in the game */
    public void generateDrawWinningMoney(int numMatches) {
        if (numSpots == 1) {
            drawWinningMoney += CalculateDrawWinningMoney.oneSpotWinnings(numMatches);
            totalGameWinningMoney += CalculateDrawWinningMoney.oneSpotWinnings(numMatches);
        } else if (numSpots == 4) {
            drawWinningMoney += CalculateDrawWinningMoney.fourSpotWinnings(numMatches);
            totalGameWinningMoney += CalculateDrawWinningMoney.fourSpotWinnings(numMatches);
        } else if (numSpots == 8) {
            drawWinningMoney += CalculateDrawWinningMoney.eightSpotWinnings(numMatches);
            totalGameWinningMoney += CalculateDrawWinningMoney.eightSpotWinnings(numMatches);
        } else if (numSpots == 10) {
            drawWinningMoney += CalculateDrawWinningMoney.tenSpotWinnings(numMatches);
            totalGameWinningMoney += CalculateDrawWinningMoney.tenSpotWinnings(numMatches);
        }
    }


    // BELOW ARE ALL GETTERS AND SETTERS

    /*This function tells us how many spots the user has currently selected */
    public int getNumSelectedSpots() {
        return selectedSpots.size();
    }

    /*This function returns the size of the random20Numbers. Used for mainly testing */
    public int getRand20NumSize() {
        return random20Numbers.size();
    }

    /*This sets the number of drawings*/
    public int getCurrentDrawNum() {
        return currentDrawNum;
    }

    /* This returns an arrayList of all the integers the user has selected*/
    public Set<Integer> getSelectedSpots() {
        ArrayList<Integer> selectedNumbers = new ArrayList<>(selectedSpots);
        return selectedSpots;
    }

    /*tells us the number of drawings left*/
    public int getNumDrawings() {
        return numDrawings;
    }

    /*tells us the number of allowed spots*/
    public int getNumSpots() {
        return numSpots;
    }

    /*tells us the number of matches*/
    public int getNumMatches() {
        return theMatchedNums.size();
    }

    /*tells us the money earned in current draw*/
    public int getDrawWinningMoney() {
        return drawWinningMoney;
    }

    /*tells us total money earned throughout game*/
    public int getTotalGameWinningMoney() {
        return totalGameWinningMoney;
    }

    /* This is for the UI. It returns a String of all the matches between 20 random numbers
     * and the user selected numbers */
    public String getMatchesString() {
        if (theMatchedNums.size() == 0) {
            return "Sorry, there is no match!";
        }
        Collections.sort(theMatchedNums);
        String numbers = "Here are the matches!\n";
        for (int i = 0; i < theMatchedNums.size() - 1; i++) {
            numbers += theMatchedNums.get(i) + ", ";
        }
        numbers += String.valueOf(theMatchedNums.get(theMatchedNums.size() - 1));
        return numbers;
    }

    /* This is for the UI. It returns an array list of the generated 20 random numbers */
    public ArrayList<Integer> get20RandNumArrayList() {
        ArrayList<Integer> numberlist = new ArrayList<>(random20Numbers);
        Collections.sort(numberlist);
        return numberlist;
    }

    public String get20RandNumString(){
        ArrayList<Integer>numberlist = new ArrayList<>(random20Numbers);
        Collections.sort(numberlist);
        String numbers = "Here are the 20 random generated numbers:\n";
        for(int i = 0; i < numberlist.size()-1; i++){
            numbers += String.valueOf(numberlist.get(i)) + ", ";
        }
        numbers += String.valueOf(numberlist.get(numberlist.size()-1));
        return numbers;
    }

}
