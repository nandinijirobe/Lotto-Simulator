// This class contains the test cases for the PlaySlip class.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PlaySlipTest {
    static PlaySlip playslip;

    @BeforeEach
    void setup() {
        playslip = new PlaySlip();
    }

    @Test
    void playSlipConstructorTest() {
        assertEquals(0, playslip.getTotalGameWinningMoney());
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
    void addSpotNumDuplicateTest() {
        playslip.addSpotNum(35);
        playslip.addSpotNum(32);
        playslip.addSpotNum(32);
        assertEquals(2, playslip.getNumSelectedSpots());
        playslip.addSpotNum(35);
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
    void removeSpotDuplicateNum() {
        playslip.addSpotNum(35);
        playslip.addSpotNum(32);
        playslip.addSpotNum(36);
        assertEquals(3, playslip.getNumSelectedSpots());
        playslip.removeSpotNum(32);
        playslip.removeSpotNum(36);
        playslip.removeSpotNum(32);
        playslip.removeSpotNum(36);
        assertEquals(1, playslip.getNumSelectedSpots());
    }

    @Test
    void addNRemoveNums() {
        playslip.addSpotNum(35);
        playslip.addSpotNum(32);
        playslip.addSpotNum(36);
        assertEquals(3, playslip.getNumSelectedSpots());
        playslip.removeSpotNum(32);
        playslip.removeSpotNum(36);
        assertEquals(1, playslip.getNumSelectedSpots());
        playslip.addSpotNum(32);
        playslip.addSpotNum(36);
        assertEquals(3, playslip.getNumSelectedSpots());
    }

    @Test
    void newRoundTest() {
        playslip.newRound(4, 4);
        assertEquals(0, playslip.getDrawWinningMoney());
        assertEquals(4, playslip.getNumDrawings());
        assertEquals(4, playslip.getNumSpots());
        assertEquals(0, playslip.getNumSelectedSpots());
        assertEquals(0, playslip.getNumMatches());
        assertEquals(0, playslip.getRand20NumSize());
    }

    @Test
    void newDrawTest() {
        playslip.newRound(2, 4);
        playslip.addSpotNum(35);
        playslip.addSpotNum(32);
        playslip.addSpotNum(36);
        playslip.addSpotNum(31);

        assertEquals(1, playslip.getCurrentDrawNum());
        playslip.newDraw();
        assertEquals(20, playslip.getRand20NumSize());

        assertEquals(2, playslip.getCurrentDrawNum());
        playslip.newDraw();
        assertEquals(20, playslip.getRand20NumSize());

        assertEquals(2, playslip.getCurrentDrawNum());
        playslip.newDraw();
        assertEquals(20, playslip.getRand20NumSize());

    }

    // check num spots unit test goes here
    @Test
    void checkNumSpotsTest() {
        playslip.newRound(2, 4);
        assertFalse(playslip.checkNumSpots());
        playslip.addSpotNum(35);
        assertFalse(playslip.checkNumSpots());
        playslip.addSpotNum(32);
        assertFalse(playslip.checkNumSpots());
        playslip.addSpotNum(36);
        assertFalse(playslip.checkNumSpots());
        playslip.addSpotNum(31);
        assertTrue(playslip.checkNumSpots());
    }

    @Test
    void quickPickTest() {
        playslip.newRound(2, 4);
        playslip.quickPick();
        assertEquals(4, (playslip.getNumSelectedSpots()));
    }

    @Test
    void checkDrawsCompletedTest() {
        playslip.newRound(3, 4);
        playslip.addSpotNum(32);
        assertFalse(playslip.checkNumSpots());
        playslip.addSpotNum(33);
        playslip.addSpotNum(34);
        playslip.addSpotNum(35);
        assertTrue(playslip.checkNumSpots());
    }

    @Test
    void generate20RandomNumTest() {
        playslip.newRound(2, 4);
        playslip.generate20RandNums();
        assertEquals(20, playslip.getRand20NumSize());
    }

    @Test
    void generateMatches() {
        playslip.newRound(2, 4);
        ArrayList<Integer> matches = new ArrayList<Integer>();
        ArrayList<Integer> winningspots = playslip.generate20RandNums();
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

    @Test
    void generateWinningMoneyTest1() {
        playslip.newRound(2, 1);
        playslip.generateDrawWinningMoney(0);
        assertEquals(0, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(1);
        assertEquals(2, playslip.getDrawWinningMoney());
    }

    @Test
    void generateWinningMoneyTest2() {
        playslip.newRound(2, 4);
        playslip.generateDrawWinningMoney(2);
        assertEquals(1, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(0);
        assertEquals(1, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(3);
        assertEquals(6, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(4);
        assertEquals(81, playslip.getDrawWinningMoney());
        assertEquals(81, playslip.getTotalGameWinningMoney());
    }


    @Test
    void generateWinningMoneyTest3() {
        /* this test only checks if the generateDrawWinning method is working
        correctly. Therefore, the num of drawings doesn't matter here */
        playslip.newRound(2, 8);
        playslip.generateDrawWinningMoney(0);
        assertEquals(0, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(1);
        assertEquals(0, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(2);
        assertEquals(0, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(3);
        assertEquals(0, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(4);
        assertEquals(2, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(5);
        assertEquals(14, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(6);
        assertEquals(64, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(7);
        assertEquals(814, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(8);
        assertEquals(10814, playslip.getDrawWinningMoney());
        assertEquals(10814, playslip.getTotalGameWinningMoney());
    }

    @Test
    void generateWinningMoneyTest4() {
        /* this test only checks if the generateDrawWinning method is working
        correctly. Therefore, the num of drawings doesn't matter here */
        playslip.newRound(2, 10);
        playslip.generateDrawWinningMoney(0);
        assertEquals(5, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(1);
        assertEquals(5, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(2);
        assertEquals(5, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(3);
        assertEquals(5, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(4);
        assertEquals(5, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(5);
        assertEquals(7, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(6);
        assertEquals(22, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(7);
        assertEquals(62, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(8);
        assertEquals(512, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(9);
        assertEquals(4762, playslip.getDrawWinningMoney());
        playslip.generateDrawWinningMoney(10);
        assertEquals(104762, playslip.getDrawWinningMoney());
        assertEquals(104762, playslip.getTotalGameWinningMoney());
    }

    @Test
    void getNumSelectedSpotsTest() {
        playslip.addSpotNum(35);
        playslip.addSpotNum(32);
        assertEquals(2, playslip.getNumSelectedSpots());
        playslip.addSpotNum(44);
        assertEquals(3, playslip.getNumSelectedSpots());
        playslip.addSpotNum(44);
        assertEquals(3, playslip.getNumSelectedSpots());
    }

    @Test
    void getRand20NumSizeTest() {
        playslip.newRound(3, 10);
        playslip.generate20RandNums();
        assertEquals(20, playslip.getRand20NumSize());
    }

    @Test
    void getNumDrawingsTest() {
        playslip.newRound(3, 10);
        assertEquals(3, playslip.getNumDrawings());
        playslip.newRound(4, 10);
        assertEquals(4, playslip.getNumDrawings());
    }

    @Test
    void getNumSpotsTest() {
        playslip.newRound(3, 4);
        assertEquals(4, playslip.getNumSpots());
        playslip.newRound(4, 8);
        assertEquals(8, playslip.getNumSpots());
    }

    @Test
    void getNumMatchesTest() {
        ArrayList<Integer> winningspots = playslip.generate20RandNums();
        playslip.addSpotNum(winningspots.get(0));
        playslip.addSpotNum(winningspots.get(2));
        playslip.addSpotNum(winningspots.get(4));
        playslip.addSpotNum(winningspots.get(5));
        playslip.generateMatches();
        assertEquals(4, playslip.getNumMatches());
    }

    @Test
    void getDrawWinningMoney() {
        playslip.newRound(2, 4);
        ArrayList<Integer> winningspots = playslip.generate20RandNums();
        playslip.addSpotNum(winningspots.get(0));
        playslip.addSpotNum(winningspots.get(2));
        playslip.addSpotNum(winningspots.get(4));
        playslip.addSpotNum(winningspots.get(5));
        playslip.generateMatches();
        playslip.generateDrawWinningMoney(playslip.getNumMatches());
        assertEquals(75, playslip.getDrawWinningMoney());
    }

    @Test
    void getTotalGameWinningMoney() {
        playslip.newRound(2, 4);
        ArrayList<Integer> winningspots = playslip.generate20RandNums();
        playslip.addSpotNum(winningspots.get(0));
        playslip.addSpotNum(winningspots.get(2));
        playslip.addSpotNum(winningspots.get(4));
        playslip.addSpotNum(winningspots.get(5));
        playslip.generateMatches();
        playslip.generateDrawWinningMoney(playslip.getNumMatches());
        assertEquals(75, playslip.getDrawWinningMoney());
        assertEquals(75, playslip.getTotalGameWinningMoney());
    }


}
