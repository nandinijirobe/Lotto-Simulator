import java.util.*;

public class PlaySlip {
    private int numSpots;
    private int numDrawings;
    private int drawWinningMoney;
    private int totalGameWinningMoney;
    private Set<Integer> selectedSpots = new HashSet<Integer>();
    private Set<Integer> winningSpots = new HashSet<Integer>();
    private ArrayList<Integer> theMatchedNums = new ArrayList<>();

    /*
    * Playslip constructor generates a playslip
    * object with given drawings and spots */
    public PlaySlip(int drawings, int spots) {
        numDrawings = drawings;
        numSpots = spots;
    }

    /*
    * Adds a number to the user's selected spot numbers */
    public void addSpotNum(int number) {
        selectedSpots.add(number);
    }

    /*
     * Removes a number to the user's selected spot numbers */
    public void removeSpotNum(int number) {
        selectedSpots.remove(number);
    }

    /*
    * New Round means that user continues playing game
    * but fills out new play slip. Hence, all values
    * except total winnings are reset
    * */
    public void newRound(int drawings, int spots) {
        numSpots = spots;
        numDrawings = drawings;
        drawWinningMoney = 0;
        selectedSpots.clear();
        winningSpots.clear();
        theMatchedNums.clear();
    }

    /*
    * New Draw means that the user continues playing the game
    * with their existing selected numbers but with newly
    * generated winning numbers. Hence, matched and winning
    * numbers are reset. */

    public void newDraw() {
        numDrawings--;
        drawWinningMoney = 0;
        winningSpots.clear();
        theMatchedNums.clear();
    }

    /*
    * This method generates random numbers for the user. The
    * number of numbers selected will depend on the numSpots
    * they have selected
    * */

    public void quickPick() {
        Random randNumber = new Random();
        while (selectedSpots.size() != numSpots) {
            selectedSpots.add(randNumber.nextInt(80) + 1);
        }
    }

    /*
    * This checks if the user has selected the right amount of spots
    * from the grid
    * */
    public boolean checkNumSpots() {
        if (selectedSpots.size() == numSpots) {
            return true;
        } else {
            return false;
        }
    }

    /*
    * This generates 20 random winning numbers
    * */
    public ArrayList<Integer> generateWinningSpots() {
        Random randNumber = new Random();
        while (winningSpots.size() != 20) {
            winningSpots.add(randNumber.nextInt(80) + 1);
        }
        ArrayList<Integer> winningSpotsList = new ArrayList<>(winningSpots);
        return winningSpotsList;
    }

    /*
    * This function calculates the total money earned for a specific draw
    * and also updates the total money earned in the game */
    public void generateDrawWinningMoney(int numMatches) {
        if (numSpots == 1) {
            drawWinningMoney += CalculateDrawWinningMoney.oneSpotWinnings(numMatches);
            totalGameWinningMoney += CalculateDrawWinningMoney.oneSpotWinnings(numMatches);
        } else if (numSpots == 4) {
            drawWinningMoney += CalculateDrawWinningMoney.fourSpotWinnings(numMatches);
            totalGameWinningMoney +=CalculateDrawWinningMoney.fourSpotWinnings(numMatches);
        } else if (numSpots == 8) {
            drawWinningMoney += CalculateDrawWinningMoney.eightSpotWinnings(numMatches);
            totalGameWinningMoney +=CalculateDrawWinningMoney.eightSpotWinnings(numMatches);
        } else if (numSpots == 10) {
            drawWinningMoney += CalculateDrawWinningMoney.tenSpotWinnings(numMatches);
            totalGameWinningMoney +=CalculateDrawWinningMoney.tenSpotWinnings(numMatches);
        }
    }

    /*
    * This function tells us how many spots the user has
    * currently selected
    * */
    public int getNumSelectedSpots() {
        return selectedSpots.size();
    } // newly added function

    public int getNumWinningSpots(){
        return winningSpots.size();
    }
    /*
    * This sets the number of drawings*/
    public void setNumDrawings(int drawings) {
        numDrawings = drawings;
    }

    /*This allows us to set num spots*/

    public void setNumSpots(int spots) {
        numSpots = spots;
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
        System.out.println("This is what the total is: "+ totalGameWinningMoney);
        return drawWinningMoney;
    }

    /*tells us total money earned throughout game*/
    public int getTotalGameWinningMoney() {
        return totalGameWinningMoney;
    }


    /*
    * uses the selected spots and winning spots set to create a set and an array
    * of all the matches\
    * */
    public ArrayList<Integer> generateMatches() {
        // Iterate through the gameNums to find a matched number with the userNums. If so, append that number to the array
        for (Integer num : winningSpots) {
            if (selectedSpots.contains(num)) {
                theMatchedNums.add(num);

            }
        }
        Collections.sort(theMatchedNums);  // Sort the matched numbers in ascending order
        return theMatchedNums;
    }

}
