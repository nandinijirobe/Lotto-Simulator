import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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

//	@Test
//	void findMatchedNumsTest() {
//		HashSet<Integer> set1 = new HashSet<>();
//		HashSet<Integer> set2 = new HashSet<>();
//
//		// Populate set1
//		set1.add(10);
//		set1.add(3);
//		set1.add(2);
//		set1.add(6);
//		set1.add(34);
//
//		// Populate set2
//		set2.add(3);
//		set2.add(7);
//		set2.add(34);
//		set2.add(72);
//
//		ArrayList<Integer> rightMatchedNums = new ArrayList<>();  // 'Controlled' array
//		rightMatchedNums.add(3);
//		rightMatchedNums.add(34);
//
//		assertEquals(true, CalculateDrawWinningMoney.findMatchedNums(set1, set2).contains(3));
//		assertEquals(true, CalculateDrawWinningMoney.findMatchedNums(set1, set2).contains(34));
//		assertEquals(false, CalculateDrawWinningMoney.findMatchedNums(set1, set2).contains(10));
//
//		assertArrayEquals(rightMatchedNums.toArray(), CalculateDrawWinningMoney.findMatchedNums(set1, set2).toArray());  // Check if the arrays share the same element values
//	}

}
