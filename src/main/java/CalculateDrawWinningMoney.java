/* This class contains the logic to caluclate all the game winnings based on
 * the number of selected number of spots and matched numbers. This assists the
 * PlaySlip class */

// Return the amount of money the player won based on the number of matches and spots (1, 4, 8, 10) they play per draw
public class CalculateDrawWinningMoney {
    // Return the amount of money the player wins based on the number of the matches they get from a single draw in a 1 Spot Game
    static int oneSpotWinnings(int totalNumMatch) {
        if (totalNumMatch == 1) {
            return 2;  // 1 match
        }
        return 0;  // Win no money if the player get 0 match
    }

    // Return the amount of money the player wins based on the number of the matches they get from a single draw in a 4 Spots Game
    static int fourSpotWinnings(int totalNumMatch) {
        if (totalNumMatch == 4) {  // 4 matches
            return 75;
        } else if (totalNumMatch == 3) {  // 3 matches
            return 5;
        } else if (totalNumMatch == 2) {  // 2 matches
            return 1;
        }

        return 0;  // Win no money if the player get 0 or 1 match
    }

    // Return the amount of money the player wins based on the number of the matches they get from a single draw in an 8 Spots Game
    static int eightSpotWinnings(int totalNumMatch) {
        if (totalNumMatch == 8) {  // 8 matches
            return 10000;
        } else if (totalNumMatch == 7) {  // 7 matches
            return 750;
        } else if (totalNumMatch == 6) {  // 6 matches
            return 50;
        } else if (totalNumMatch == 5) {  // 5 matches
            return 12;
        } else if (totalNumMatch == 4) {  // 4 matches
            return 2;
        }

        return 0;  // Win no money if the player get 0, 1, 2, or 3 matches
    }

    // Return the amount of money the player wins based on the number of the matches they get from a single draw in a 10 Spots Game
    static int tenSpotWinnings(int totalNumMatch) {
        if (totalNumMatch == 10) {  // 10 matches
            return 100000;
        } else if (totalNumMatch == 9) {  // 9 matches
            return 4250;
        } else if (totalNumMatch == 8) {  // 8 matches
            return 450;
        } else if (totalNumMatch == 7) {  // 7 matches
            return 40;
        } else if (totalNumMatch == 6) {  // 6 matches
            return 15;
        } else if (totalNumMatch == 5) {  // 5 matches
            return 2;
        } else if (totalNumMatch == 0) {  // 0 match
            return 5;
        }

        return 0;  // Win no money if the player get 1, 2, 3, or 4 matches
    }


}
