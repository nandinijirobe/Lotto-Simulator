import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class PlaySlip {
    private int totalGameWinningMoney;
    private int drawWinningMoney;
    private int numDrawings;
    private int numSpots;
    private Set<Integer> selectedSpots = new HashSet<Integer>();
    private Set<Integer> winningSpots = new HashSet<Integer>();
    private Set<Integer> theMatchedSums = new HashSet<Integer>();

    public PlaySlip(){

    }

    public void addSpotNum(int number){
        selectedSpots.add(number);
    }

    public void removeSpotNum(int number) {
        selectedSpots.remove(number);
    }

    public void newRound(){
        drawWinningMoney = 0;
        numDrawings = 0;
        numSpots = 0;
        selectedSpots.clear();
        winningSpots.clear();
        theMatchedSums.clear();
    }

    public void newDraw(){
        winningSpots.clear();
        theMatchedSums.clear();
    }

    public void quickPick(){
        // generates random numbers for the user
    }

    private boolean checkNumSpots(){
        return false;
    }

    private ArrayList<Integer> generateWinningSpots(){
        ArrayList<Integer> x = new ArrayList<Integer>();
        return x;
    }

    private void generateDrawWinningMoney(int numMatches){
//        if(numSpots == 1) {
//            oneSpotWinnings(numMatches);
//        } else if (numSpots == 4) {
//            fourSpotWinnings(numMatches);
//        } else if (numSpots == 8) {
//            eightSpotWinnings(numMatches);
//        } else if (numSpots == 10) {
//            tenSpotWinnings(numMatches);
//        }
    }

    public void setNumDrawings(int drawings){
        numDrawings = drawings;
    }
    public void setNumSpots(int spots){
        numSpots = spots;
    }
    public int getNumDrawings(){
        return numDrawings;
    }
    public int getNumSpots(){
        return numSpots;
    }
    public int getNumMatches(){
        return 0;
    }
    public int getDrawWinningMoney(){
        return drawWinningMoney;
    }
    public int getTotalGameWinningMoney(){
        return totalGameWinningMoney;
    }
    public ArrayList<Integer> getTheMatchedNums(){
        ArrayList<Integer> x = new ArrayList<Integer>();
        return x;
    }

}
