import java.util.*;
// Return the amount of money the player won based on the number of matches and spots (1, 4, 8, 10) they play per draw
public class CalculateDrawWinningMoney {
    static int oneSpotWinnings() {
        return 2;  // 1 match
    }

    static int fourSpotWinnings(int totalNumMatch) {
        if (totalNumMatch == 4) {  // 4 matches
            return 75;
        } else if (totalNumMatch == 3) {  // 3 matches
            return 5;
        } else if (totalNumMatch == 2) {  // 2 matches
            return 1;
        }

        return 0;  // 0 or 1 match
    }

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

        return 0;  // 0-3 matches
    }

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

        return 0;  // 1-4 matches
    }

    static ArrayList<Integer> findMatchedNums(HashSet<Integer> userNums, HashSet<Integer> gameNums) {
        ArrayList<Integer> matchedNums = new ArrayList<>();

        // Iterate through the gameNums to find a matched number with the userNums. If so, append that number to the array
        for (Integer num : gameNums) {
            if (userNums.contains(num)) {
                matchedNums.add(num);
            }
        }

        Collections.sort(matchedNums);  // Sort the matched numbers in ascending order

        return matchedNums;
    }
}
