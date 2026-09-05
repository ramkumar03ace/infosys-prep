import java.util.ArrayList;
import java.util.List;

public class q3 {

    /**
     * Finds the minimum number of adjacent swaps required to make all identical
     * characters of at least one type ('0' or '1') appear together in one
     * contiguous block.
     *
     * You have two choices:
     * Choice 1: Group all '1's together.
     * Choice 2: Group all '0's together.
     *
     * Returns the minimum swaps between the two choices.
     *
     * @param s The input binary string consisting of '0' and '1'
     * @return Minimum number of adjacent swaps required
     */
    public static long minAdjacentSwaps(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }

        // Choice 1: Group all '1's together
        long swapsFor1 = getMinSwapsForChar(s, '1');

        // Choice 2: Group all '0's together
        long swapsFor0 = getMinSwapsForChar(s, '0');

        // Return the minimum of the two choices
        return Math.min(swapsFor1, swapsFor0);
    }

    /**
     * Calculates the minimum adjacent swaps required to gather all occurrences
     * of a target character into a single contiguous block anywhere in the string.
     *
     * Approach:
     * 1. Collect all original 0-based indices where s.charAt(i) == target.
     * 2. If there are 0 or 1 occurrences, they are already contiguous -> 0 swaps.
     * 3. Normalize indices: A[i] = pos[i] - i.
     * Why? If the contiguous block starts at index 'start', the i-th occurrence
     * must end up at (start + i).
     * Distance = |pos[i] - (start + i)| = |(pos[i] - i) - start| = |A[i] - start|.
     * 4. The value of 'start' that minimizes sum(|A[i] - start|) is the MEDIAN of
     * array A.
     * 5. Since pos is strictly increasing, A[i] = pos[i] - i is non-decreasing
     * (sorted).
     * Hence, the median is simply A[m / 2].
     */
    public static long getMinSwapsForChar(String s, char target) {
        List<Integer> pos = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == target) {
                pos.add(i);
            }
        }

        int m = pos.size();
        if (m <= 1) {
            return 0; // 0 or 1 occurrence is already in a contiguous block
        }

        // The median element index in the collected positions
        int mid = m / 2;
        int medianVal = pos.get(mid) - mid;

        // Sum of absolute differences to the median
        long swaps = 0;
        for (int i = 0; i < m; i++) {
            int currentVal = pos.get(i) - i;
            swaps += Math.abs(currentVal - medianVal);
        }

        return swaps;
    }

    /**
     * Alternative interpretation:
     * If the problem requires moving all identical characters to the boundaries
     * (e.g., all '0's to the left & all '1's to the right, or vice-versa).
     *
     * This is calculated using inversion counts:
     * - Moving all '0's to left = count how many '1's appear before each '0'.
     * - Moving all '1's to left = count how many '0's appear before each '1'.
     */
    public static long minAdjacentSwapsToBoundary(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }

        long zerosToLeft = 0;
        long onesSeen = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                onesSeen++;
            } else {
                zerosToLeft += onesSeen;
            }
        }

        long totalZeros = s.length() - onesSeen;
        long onesToLeft = (onesSeen * totalZeros) - zerosToLeft;

        return Math.min(zerosToLeft, onesToLeft);
    }

    public static void main(String[] args) {
        // Test Case 1: "01010"
        // '1's are at indices 1, 3. Median grouping takes 1 swap ("01100" or "00110").
        // '0's are at indices 0, 2, 4. Median grouping takes 2 swaps ("10001").
        // min(1, 2) = 1.
        String s1 = "01010";
        System.out.println("Test Case 1: S = \"" + s1 + "\"");
        System.out.println("  Swaps to group '1's together: " + getMinSwapsForChar(s1, '1'));
        System.out.println("  Swaps to group '0's together: " + getMinSwapsForChar(s1, '0'));
        System.out.println("  Optimal Result: " + minAdjacentSwaps(s1));
        System.out.println("  Boundary Result: " + minAdjacentSwapsToBoundary(s1));
        System.out.println();

        // Test Case 2: "11100" -> already grouped
        String s2 = "11100";
        System.out.println("Test Case 2: S = \"" + s2 + "\"");
        System.out.println("  Optimal Result: " + minAdjacentSwaps(s2));
        System.out.println();

        // Test Case 3: "0100101"
        // '1's at [1, 4, 6] -> A = [1-0, 4-1, 6-2] = [1, 3, 4]. Med = 3 -> |1-3| +
        // |3-3| + |4-3| = 2 + 0 + 1 = 3 swaps.
        // '0's at [0, 2, 3, 5] -> B = [0, 1, 1, 2]. Med = 1 -> |0-1| + |1-1| + |1-1| +
        // |2-1| = 1 + 0 + 0 + 1 = 2 swaps.
        // min(3, 2) = 2.
        String s3 = "0100101";
        System.out.println("Test Case 3: S = \"" + s3 + "\"");
        System.out.println("  Swaps to group '1's together: " + getMinSwapsForChar(s3, '1'));
        System.out.println("  Swaps to group '0's together: " + getMinSwapsForChar(s3, '0'));
        System.out.println("  Optimal Result: " + minAdjacentSwaps(s3));
        System.out.println("  Boundary Result: " + minAdjacentSwapsToBoundary(s3));
    }
}

/*
 * =============================================================================
 * ===========
 * DETAILED EXPLANATION
 * =============================================================================
 * ===========
 * 
 * 1. PROBLEM SUMMARY:
 * We are given a binary string S consisting only of '0' and '1'.
 * An adjacent swap allows swapping any two adjacent characters: S[i] and
 * S[i+1].
 * We want to find the minimum number of adjacent swaps so that ALL occurrences
 * of
 * either '1' OR '0' form a single contiguous block.
 * 
 * Choices:
 * Choice 1: Group all '1's together (e.g., "0011100" - all '1's in one block).
 * Choice 2: Group all '0's together (e.g., "1100011" - all '0's in one block).
 * Return min(Choice 1, Choice 2).
 * 
 * -----------------------------------------------------------------------------
 * -----------
 * 2. WHY THE MEDIAN APPROACH IS OPTIMAL:
 * Suppose target character '1' appears at original 0-indexed positions:
 * pos[0], pos[1], pos[2], ..., pos[m - 1]
 * 
 * If we place this block of m characters starting at index 'start', their final
 * positions will be:
 * start, start + 1, start + 2, ..., start + m - 1
 * 
 * Because adjacent swaps preserve relative order (we never cross identical
 * elements),
 * the i-th character moves from pos[i] to (start + i).
 * 
 * The number of adjacent swaps equals the sum of distances:
 * Cost = SUM |pos[i] - (start + i)| for i = 0 to m - 1
 * 
 * Rearranging each term:
 * |pos[i] - (start + i)| = |(pos[i] - i) - start|
 * 
 * Let A[i] = pos[i] - i.
 * Then:
 * Cost = SUM |A[i] - start|
 * 
 * MATH THEOREM (L1 Minimization):
 * The value of 'start' that minimizes SUM |A[i] - start| is the MEDIAN of the
 * array A.
 * - Since pos[i+1] > pos[i], we have:
 * pos[i+1] - (i + 1) >= pos[i] - i
 * So array A is already sorted in non-decreasing order!
 * - Therefore, the median is simply:
 * medianVal = A[m / 2] = pos[m / 2] - (m / 2)
 * - Total swaps = SUM |A[i] - medianVal|
 * 
 * -----------------------------------------------------------------------------
 * -----------
 * 3. STEP-BY-STEP TRACE FOR S = "01010":
 * - Choice 1: Group '1's
 * Indices of '1': pos = [1, 3] (m = 2)
 * A[0] = 1 - 0 = 1
 * A[1] = 3 - 1 = 2
 * Median index = 2 / 2 = 1 -> medianVal = A[1] = 2
 * Swaps for '1' = |1 - 2| + |2 - 2| = 1 + 0 = 1 swap.
 * (Resulting string: "00110", by swapping S[1] and S[2])
 * 
 * - Choice 2: Group '0's
 * Indices of '0': pos = [0, 2, 4] (m = 3)
 * B[0] = 0 - 0 = 0
 * B[1] = 2 - 1 = 1
 * B[2] = 4 - 2 = 2
 * Median index = 3 / 2 = 1 -> medianVal = B[1] = 1
 * Swaps for '0' = |0 - 1| + |1 - 1| + |2 - 1| = 1 + 0 + 1 = 2 swaps.
 * (Resulting string: "10001")
 * 
 * - Final Answer:
 * min(Choice 1, Choice 2) = min(1, 2) = 1.
 * 
 * -----------------------------------------------------------------------------
 * -----------
 * 4. COMPLEXITY:
 * - Time Complexity: O(N)
 * One pass to collect indices: O(N)
 * One pass to compute distance to median: O(m) <= O(N)
 * Total time: O(N), which easily handles N up to 10^5 or 10^6.
 * 
 * - Space Complexity: O(N)
 * ArrayList to store the indices of '1' and '0'.
 * =============================================================================
 * ===========
 */

