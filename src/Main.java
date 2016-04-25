import java.util.Arrays;

/**
 * Created by Allen on 4/23/2016.
 */
public class Main {

	private static int[] testList = {
	/*Set 1:*/ 25, 51, 43, 100, 58};
//	/*Set 2:*/ 53, 68, 75, 94, 97};
//	/*Set 3:*/ 9, 10, 58, 78, 88};
//	/*Set 4:*/ 50, 54, 75, 78, 82};
//	/*Set 5:*/ 31, 63, 76, 78, 90};
//	/*Set 6:*/ 2, 13, 65, 68, 84};
//	/*Set 7:*/ 2, 36, 68, 73, 89};
//	/*Set 8:*/ 20, 25, 41, 49, 92};
//	/*Set 9:*/ 24, 25, 27, 44, 87};
//	/*Set 10:*/ 6, 7, 27, 56, 67};
// 	/*Set 11:*/ 6, 4, 1, 3, 2, 5};
//	/*Set 12:*/ 100, 15, 300, 30, 60, 200};
//	/*Set 13:*/ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//	/*Set 14:*/ 10, 20, 90, 100, 200};
//	/*Set 15:*/ 3, 3, 2, 2, 2};
//	/*Set 16:*/ 0, 1, 5, 6};
//	/*Set 17:*/ 4, 14, 15, 16, 17};
//	/*Set 18:*/ -2, -1, 1, 2, 3, 4, 5};


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
		int halfSum = (Arrays.stream(integerList).sum()) / 2;
		int tempHalfSum;
		int errorMargin;
		int startValue;
		splitArray = new int[listLength][listLength];

		for (int numElements = 0; numElements < listLength - 2; ++numElements) {
			errorMargin = numElements;
			startValue = listLength - 1;
			tempHalfSum = halfSum;

			//Check for a value equal to half the sum of integerList, plus or minus
			//an error margin
			if (halfSumIsPresent((tempHalfSum + errorMargin), integerList)) {
				splitArray = halfSumFoundFillArray(integerList, tempHalfSum);
			}
			else if (halfSumIsPresent((tempHalfSum - errorMargin), integerList)) {
				splitArray = halfSumFoundFillArray(integerList, tempHalfSum);
			}
			//Else start at the bottom of the array and try to add to a value approximate
			//to the half sum + an error margin which increases every cycle
			else {
				for (; startValue >= 0; --startValue) {
					if (integerList[startValue] < (tempHalfSum)) {
						tempHalfSum -= Math.abs(integerList[startValue]);
					}
				}
			}
		}
		return splitArray;
	}

	/**     //TODO COMPLETE
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
//		//Check for a value equal to half the sum of integerList
//		if (halfSumIsPresent(halfSum, integerList)) {
//			splitArray = halfSumFoundFillArray(integerList, halfSum);
//		}
//		//Else start at the bottom of the array and use a greedy method variant
//		//to assign the next largest value into the column with the lesser sum.
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
//			//If negative values are present, assign the next largest value into the
//			//column with the greater sum.
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
			if (index != halfSumIndex) {
				tempArray[index][1] = integerList[index];
			}
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
		while (integerList[index] != halfSum) {
			index--;
		}
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
			if (integerList[index] == halfSum) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param testList
	 */
	public static void printTestList(int[] testList) {
		System.out.println("**********Printing testList:**********");
		System.out.println(Arrays.toString(testList));
		System.out.println();
	}

	/**
	 * @param myArray
	 */
	public static void printTwoDArray(int[][] myArray) {
		int sumFirstColumn = 0;
		int sumSecondColumn = 0;
		System.out.println("**********Printing splitEvenly:**********");
		System.out.println("Column 1" + "\t" + "Column 2");
		for (int rIndex = 0; rIndex < myArray.length; ++rIndex) {
			for (int cIndex = 0; cIndex < 2; ++cIndex) {
				System.out.print("  " + myArray[rIndex][cIndex] + " \t\t\t");
			}
			System.out.println();
			sumFirstColumn += myArray[rIndex][0];
			sumSecondColumn += myArray[rIndex][1];
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
