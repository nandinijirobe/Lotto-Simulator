import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

class PlaySlipTest {
    static PlaySlip playslip;

    @BeforeEach
    void setup() {
        playslip = new PlaySlip(2, 4);
    }

    @Test
    void playSlipConstructorTest() {
        assertEquals(4, playslip.getNumSpots());
        assertEquals(2, playslip.getNumDrawings());
    }

    @Test
    void addSpotNumTest() {
        playslip.addSpotNum(35);
        playslip.addSpotNum(32);
        assertEquals(2, playslip.getNumSelectedSpots());
        playslip.addSpotNum(44);
        assertEquals(3, playslip.getNumSelectedSpots());
    }

    @Test
    void removeSpotNum() {
        playslip.addSpotNum(35);
        playslip.addSpotNum(32);
        playslip.addSpotNum(36);
        assertEquals(3, playslip.getNumSelectedSpots());
        playslip.removeSpotNum(32);
        playslip.removeSpotNum(36);
        assertEquals(1, playslip.getNumSelectedSpots());
    }

    @Test
    void newRoundTest() {
        playslip.newRound(4,4);
        assertEquals(0, playslip.getDrawWinningMoney());
        assertEquals(4, playslip.getNumDrawings());
        assertEquals(4, playslip.getNumSpots());
        assertEquals(0, playslip.getNumSelectedSpots());
        assertEquals(0, playslip.getNumMatches());
        assertEquals(0, playslip.getNumWinningSpots());
    }

    @Test
    void newDrawTest() {
        assertEquals(0, playslip.getNumMatches());
        assertEquals(0, playslip.getNumWinningSpots());
    }

    @Test
    void quickPickTest(){
        playslip.quickPick();
        assertTrue((playslip.getNumSelectedSpots()) == 4);
    }

    @Test
    void checkNumSpotTest(){
        playslip.addSpotNum(35);
        assertEquals(false, playslip.checkNumSpots());
        playslip.addSpotNum(35);
        playslip.addSpotNum(36);
        playslip.addSpotNum(37);
        assertEquals(false, playslip.checkNumSpots());
        playslip.addSpotNum(39);
        assertEquals(true, playslip.checkNumSpots());
        playslip.addSpotNum(40);
        assertEquals(false, playslip.checkNumSpots());
    }


    @Test
    void generateWinningSpotsTest(){
        ArrayList<Integer> nums = playslip.generateWinningSpots();
        assertEquals(20, nums.size());
    }

    @Test
    void generateWinningMoneyTest(){
        playslip.generateDrawWinningMoney(2);
        assertEquals(1, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(3);
        assertEquals(6, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(4);
        assertEquals(81, playslip.getDrawWinningMoney());
        assertEquals(81, playslip.getTotalGameWinningMoney());
    }

    @Test
    void getNumSelectedSpots(){
        playslip.addSpotNum(35);
        playslip.addSpotNum(32);
        assertEquals(2, playslip.getNumSelectedSpots());
        playslip.addSpotNum(44);
        assertEquals(3, playslip.getNumSelectedSpots());
        playslip.addSpotNum(44);
        assertEquals(3, playslip.getNumSelectedSpots());
    }

    @Test
    void getNumWinningSpots(){
        playslip.generateWinningSpots();
        assertEquals(20, playslip.getNumWinningSpots());
    }

    @Test
    void setNumDrawings(){
        assertEquals(2, playslip.getNumDrawings());
        playslip.setNumDrawings(3);
        assertEquals(3, playslip.getNumDrawings());
    }

    @Test
    void setNumSpots(){
        assertEquals(4, playslip.getNumSpots());
        playslip.setNumSpots(8);
        assertEquals(8, playslip.getNumSpots());
    }

    @Test
    void getNumDrawings(){
        assertEquals(2, playslip.getNumDrawings());
        playslip.setNumDrawings(3);
        assertEquals(3, playslip.getNumDrawings());
    }

    @Test
    void getNumSpots(){
        assertEquals(4, playslip.getNumSpots());
        playslip.setNumSpots(8);
        assertEquals(8, playslip.getNumSpots());
    }

    @Test
    void getNumMatches(){
        ArrayList<Integer> winningspots = playslip.generateWinningSpots();
        playslip.addSpotNum(winningspots.get(0));
        playslip.addSpotNum(winningspots.get(2));
        playslip.addSpotNum(winningspots.get(4));
        playslip.addSpotNum(winningspots.get(5));
        playslip.generateMatches();
        assertEquals(4, playslip.getNumMatches());
    }

    @Test
    void getDrawWinningMoney(){
        ArrayList<Integer> winningspots = playslip.generateWinningSpots();
        playslip.addSpotNum(winningspots.get(0));
        playslip.addSpotNum(winningspots.get(2));
        playslip.addSpotNum(winningspots.get(4));
        playslip.addSpotNum(winningspots.get(5));
        playslip.generateMatches();
        playslip.generateDrawWinningMoney(playslip.getNumMatches());
        assertEquals(75, playslip.getDrawWinningMoney());
    }

    @Test
    void getTotalGameWinningMoney(){
        ArrayList<Integer> winningspots = playslip.generateWinningSpots();
        playslip.addSpotNum(winningspots.get(0));
        playslip.addSpotNum(winningspots.get(2));
        playslip.addSpotNum(winningspots.get(4));
        playslip.addSpotNum(winningspots.get(5));
        playslip.generateMatches();
        playslip.generateDrawWinningMoney(playslip.getNumMatches());
        assertEquals(75, playslip.getDrawWinningMoney());
        assertEquals(75, playslip.getTotalGameWinningMoney());
    }

    @Test
    void generateMatches(){
        ArrayList<Integer>matches = new ArrayList<Integer>();
        ArrayList<Integer> winningspots = playslip.generateWinningSpots();
        playslip.addSpotNum(winningspots.get(0));
        playslip.addSpotNum(winningspots.get(2));
        playslip.addSpotNum(winningspots.get(4));
        playslip.addSpotNum(winningspots.get(5));
        matches.add(winningspots.get(0));
        matches.add(winningspots.get(2));
        matches.add(winningspots.get(4));
        matches.add(winningspots.get(5));
        Collections.sort(matches);
        assertEquals(matches, playslip.generateMatches());
    }


}
