import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalcDrawWinningsTest {

    @Test
    void oneSpotWinningsTest() {
        assertEquals(2, CalculateDrawWinningMoney.oneSpotWinnings(1));
        assertEquals(0, CalculateDrawWinningMoney.oneSpotWinnings(0));
    }

    @Test
    void fourSpotWinningsTest() {
        assertEquals(75, CalculateDrawWinningMoney.fourSpotWinnings(4));
        assertEquals(5, CalculateDrawWinningMoney.fourSpotWinnings(3));
        assertEquals(1, CalculateDrawWinningMoney.fourSpotWinnings(2));
        assertEquals(0, CalculateDrawWinningMoney.fourSpotWinnings(1));
        assertEquals(0, CalculateDrawWinningMoney.fourSpotWinnings(0));
    }

    @Test
    void eightSpotWinningsTest() {
        assertEquals(10000, CalculateDrawWinningMoney.eightSpotWinnings(8));
        assertEquals(750, CalculateDrawWinningMoney.eightSpotWinnings(7));
        assertEquals(50, CalculateDrawWinningMoney.eightSpotWinnings(6));
        assertEquals(12, CalculateDrawWinningMoney.eightSpotWinnings(5));
        assertEquals(2, CalculateDrawWinningMoney.eightSpotWinnings(4));
        assertEquals(0, CalculateDrawWinningMoney.eightSpotWinnings(3));
        assertEquals(0, CalculateDrawWinningMoney.eightSpotWinnings(2));
        assertEquals(0, CalculateDrawWinningMoney.eightSpotWinnings(1));
        assertEquals(0, CalculateDrawWinningMoney.eightSpotWinnings(0));

    }

    @Test
    void tenSpotWinningsTest() {
        assertEquals(100000, CalculateDrawWinningMoney.tenSpotWinnings(10));
        assertEquals(4250, CalculateDrawWinningMoney.tenSpotWinnings(9));
        assertEquals(450, CalculateDrawWinningMoney.tenSpotWinnings(8));
        assertEquals(40, CalculateDrawWinningMoney.tenSpotWinnings(7));
        assertEquals(15, CalculateDrawWinningMoney.tenSpotWinnings(6));
        assertEquals(2, CalculateDrawWinningMoney.tenSpotWinnings(5));
        assertEquals(0, CalculateDrawWinningMoney.tenSpotWinnings(4));
        assertEquals(0, CalculateDrawWinningMoney.tenSpotWinnings(3));
        assertEquals(0, CalculateDrawWinningMoney.tenSpotWinnings(2));
        assertEquals(0, CalculateDrawWinningMoney.tenSpotWinnings(1));
        assertEquals(5, CalculateDrawWinningMoney.tenSpotWinnings(0));

    }


}
