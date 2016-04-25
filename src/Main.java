import java.util.Arrays;

/**
 * Created by Allen on 4/23/2016.
 */
public class Main {

	private static int[] testList = {
// 	/*Set 1:*/ 6, 4, 1, 3, 2, 5};               // No discrepancy
//	/*Set 2:*/ 100, 15, 300, 30, 60, 200};      // Sums the same. Difference in sub value assignment
//	/*Set 3:*/ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};  // Sums the same. Difference in sub value assignment
//	/*Set 4:*/ 10, 20, 90, 100, 200};           // No discrepancy
//	/*Set 5:*/ 3, 3, 2, 2, 2};                  // Discrepancy between A and B
//	/*Set 6:*/ 0, 1, 5, 6};                     // No discrepancy
//	/*Set 7:*/ 4, 14, 15, 16, 17};              // Discrepancy between A and B
//	/*Set 8:*/ -2, -1, 1, 2, 3, 4, 5};          // No discrepancy
//	/*Set 9:*/ 1, 1, 2, 1, 1, 1, 1, 1, 1, 6};   // Sums the same. Difference in sub value assignment
	/*Set 10:*/ 1,2,2,3,5,6,1};                 // Sums the same. Difference in sub value assignment

	/**
	 * Algorithm A: The More Accurate Algorithm
	 *
	 * @param integerList
	 * @return
	 * @throws InvalidInputException
	 */
	public int[][] splitEvenly(int[] integerList) throws InvalidInputException {
		int listLength = integerList.length;
		int[][] splitArray;
		int errorMargin;
		int halfSum = (Arrays.stream(integerList).sum()) / 2;
		splitArray = new int[listLength][listLength];

		for (int numElements = 0; numElements < listLength - 2; ++numElements) {
			errorMargin = numElements;
			int tempHalfSum = halfSum;
			boolean sumFound = false;

			// Check for a value equal to half the sum of integerList, plus or minus
			// an error margin
			if (halfSumIsPresent((tempHalfSum + errorMargin), integerList)) {
				sumFound = true;
				splitArray = halfSumFoundFillArray(integerList, tempHalfSum);
			} else if (halfSumIsPresent((tempHalfSum - errorMargin), integerList)) {
				sumFound = true;
				splitArray = halfSumFoundFillArray(integerList, tempHalfSum);
			}
			// Else start at the bottom of the array and try to identify sub-values which
			// add up to a value approximate to the half sum + an error margin that
			// increases every cycle
			else {
				int[][] tempArray = new int[listLength][listLength];
				for (int endValue = listLength - 1; endValue >= 0; --endValue) {
					if (integerList[endValue] <= (tempHalfSum)) {
						tempHalfSum = Math.abs(tempHalfSum) - Math.abs(integerList[endValue]);
						tempArray[endValue][1] = 1;
					}
					if (tempHalfSum == errorMargin) {
						sumFound = true;
						splitArray = subValuesFoundFillArray(tempArray, integerList);
					}
					if (sumFound) {break;}
				}
				if(!sumFound){splitArray = subValuesFoundFillArray(tempArray, integerList);}
			}
			if (sumFound) {break;}
		}
		return splitArray;
	}


	/**
	 * Algorithm B: The Faster Algorithm
	 *
	 * @param integerList
	 * @return
	 * @throws InvalidInputException
	 */
//	public int[][] splitEvenly(int[] integerList) throws InvalidInputException {
//		int listLength = integerList.length;
//		int[][] splitArray;
//		int halfSum = (Arrays.stream(integerList).sum()) / 2;
//
//		// Check for a value equal to half the sum of integerList
//		if (halfSumIsPresent(halfSum, integerList)) {
//			splitArray = halfSumFoundFillArray(integerList, halfSum);
//		}
//		// Else start at the bottom of the array and use a greedy method variant
//		// to assign the next largest value into the column with the lesser sum.
//		else {
//			splitArray = new int[listLength][listLength];
//			splitArray[listLength - 1][1] = integerList[listLength - 1];
//			int sumSecondColumn = integerList[listLength - 1];
//			int sumFirstColumn = 0;
//			int negativeIndex = 0;
//			for (int count = listLength - 2; count >= 0; --count) {
//				if (integerList[count] < 0) {
//					negativeIndex = count;
//					break;
//				}
//				if (sumFirstColumn < sumSecondColumn) {
//					splitArray[count][0] = integerList[count];
//					sumFirstColumn += integerList[count];
//				} else {
//					splitArray[count][1] = integerList[count];
//					sumSecondColumn += integerList[count];
//				}
//			}
//			// If negative values are present, assign the next largest value into the
//			// column with the greater sum.
//			if (integerList[negativeIndex] < 0) {
//				for (int index = 0; index < negativeIndex + 1; ++index) {
//					if (sumFirstColumn < sumSecondColumn) {
//						splitArray[index][1] = integerList[index];
//						sumSecondColumn += integerList[index];
//					} else {
//						splitArray[index][0] = integerList[index];
//						sumFirstColumn += integerList[index];
//					}
//				}
//			}
//		}
//		return splitArray;
//	}

	//Private Helper Methods
	/**
	 * Fills the second column of a 2D array with sub values found that add up
	 * to the half sum. Puts the remaining values in the first column.
	 * @param preppedArray
	 * @param integerList
	 * @return
	 */
	private int[][] subValuesFoundFillArray(int[][] preppedArray, int[] integerList) {
		int listLength = integerList.length;
		int[][] tempArray = new int[listLength][listLength];
		for (int index = 0; index < listLength; ++index) {
			if (preppedArray[index][1] == 1) {
				tempArray[index][1] = integerList[index];
			} else {
				tempArray[index][0] = integerList[index];
			}
		}
		return tempArray;
	}

	/**
	 * This method assumes that a single half sum value is present in the integerList.
	 * Populates the first column with the half sum and puts the remaining values in
	 * the second column of a 2D array.
	 *
	 * @param integerList
	 * @param halfSum
	 * @return
	 */
	private int[][] halfSumFoundFillArray(int[] integerList, int halfSum) {
		int listLength = integerList.length;
		int[][] tempArray = new int[listLength][listLength];
		int halfSumIndex = getHalfSumIndex(halfSum, integerList);
		tempArray[0][0] = integerList[halfSumIndex];
		for (int index = 0; index < listLength - 1; ++index) {
			if (index != halfSumIndex) {tempArray[index][1] = integerList[index];}
		}
		return tempArray;
	}

	/**
	 * This method assumes that a single half sum value is present in the integerList
	 * Returns the index of a single value equaling half the sum of the integerList
	 *
	 * @param halfSum
	 * @param integerList
	 * @return
	 */
	private int getHalfSumIndex(int halfSum, int[] integerList) {
		int index = integerList.length - 1;
		while (integerList[index] != halfSum) {index--;}
		return index;
	}

	/**
	 * Checks if a single value equaling half of the sum of integerList is present
	 *
	 * @param halfSum
	 * @param integerList
	 * @return
	 */
	private boolean halfSumIsPresent(int halfSum, int[] integerList) {
		for (int index = integerList.length - 1; index >= 0; --index) {
			if (integerList[index] == halfSum) {return true;}
		}
		return false;
	}

	/**
	 * @param testList
	 */
	private static void printTestList(int[] testList) {
		System.out.println("**********Printing testList:**********");
		System.out.println(Arrays.toString(testList));
		System.out.println();
	}

	/**
	 * @param myArray
	 */
	private static void printTwoDArray(int[][] myArray) {
		int sumFirstColumn = 0;
		int sumSecondColumn = 0;
		System.out.println("**********Printing splitEvenly:**********");
		System.out.println("Column 1" + "\t" + "Column 2");
		for (int[] aMyArray : myArray) {
			for (int cIndex = 0; cIndex < 2; ++cIndex) {
				System.out.print("  " + aMyArray[cIndex] + " \t\t\t");
			}
			System.out.println();
			sumFirstColumn += aMyArray[0];
			sumSecondColumn += aMyArray[1];
		}
		System.out.println("Sum: " + sumFirstColumn + "\t\t  Sum: " + sumSecondColumn);
	}


	public static void main(String[] args) throws InvalidInputException {
		Arrays.sort(testList);
		Main test = new Main();
		int[][] myArray = test.splitEvenly(testList);

		printTestList(testList);
		printTwoDArray(myArray);

	}
}
