import java.util.HashMap;
import java.util.Map;

public class q2 {

    /**
     * Solves the minimum total dominance score problem.
     * 
     * @param arr The input array of N integers
     * @param K   The number of non-empty contiguous subarrays to partition into
     * @return The minimum total dominance score
     */
    public static int getMinDominanceScore(int[] arr, int K) {
        int n = arr.length;
        if (K > n || K <= 0)
            return -1; // Impossible to partition

        // dp[k][i] = min dominance score of dividing first i elements into k subarrays
        int[][] dp = new int[K + 1][n + 1];

        // Initialize DP table with infinity
        for (int k = 0; k <= K; k++) {
            for (int i = 0; i <= n; i++) {
                dp[k][i] = Integer.MAX_VALUE / 2;
            }
        }
        dp[0][0] = 0; // 0 elements with 0 partitions costs 0

        // DP state transitions
        for (int k = 1; k <= K; k++) {
            for (int i = k; i <= n; i++) {
                Map<Integer, Integer> freqMap = new HashMap<>();
                int maxFreq = 0;

                // Move backward from i-1 down to k-1 to evaluate last subarray arr[j ... i-1]
                for (int j = i - 1; j >= k - 1; j--) {
                    int val = arr[j];
                    int count = freqMap.getOrDefault(val, 0) + 1;
                    freqMap.put(val, count);
                    maxFreq = Math.max(maxFreq, count);

                    if (dp[k - 1][j] != Integer.MAX_VALUE / 2) {
                        dp[k][i] = Math.min(dp[k][i], dp[k - 1][j] + maxFreq);
                    }
                }
            }
        }

        return dp[K][n];
    }

    /**
     * Solves the maximum total dominance score problem.
     * 
     * @param arr The input array of N integers
     * @param K   The number of non-empty contiguous subarrays to partition into
     * @return The maximum total dominance score
     */
    public static int getMaxDominanceScore(int[] arr, int K) {
        int n = arr.length;
        if (K > n || K <= 0)
            return -1;

        int[][] dp = new int[K + 1][n + 1];

        for (int k = 1; k <= K; k++) {
            for (int i = k; i <= n; i++) {
                Map<Integer, Integer> freqMap = new HashMap<>();
                int maxFreq = 0;

                for (int j = i - 1; j >= k - 1; j--) {
                    int val = arr[j];
                    int count = freqMap.getOrDefault(val, 0) + 1;
                    freqMap.put(val, count);
                    maxFreq = Math.max(maxFreq, count);

                    if (k == 1 || dp[k - 1][j] > 0) {
                        dp[k][i] = Math.max(dp[k][i], dp[k - 1][j] + maxFreq);
                    }
                }
            }
        }

        return dp[K][n];
    }

    public static void main(String[] args) {
        // Example test case based on problem description
        int[] arr = { 2, 3, 2, 4, 2 };
        int K = 2;
        int[] arr2 = { 1, 1, 2, 2, 3, 3 };
        int K2 = 3;
        System.out.println("Array: [2, 3, 2, 4, 2], K = " + K);
        System.out.println("Minimum Dominance Score: " + getMinDominanceScore(arr, K));
        System.out.println("Maximum Dominance Score: " + getMaxDominanceScore(arr, K));
        System.out.println("Maximum Dominance Score: " + getMaxDominanceScore(arr2, K2));
    }
}

/*
========================================================================================
                                LOGIC & DP EXPLANATION
========================================================================================

1. CORE PROBLEM & DEFINITIONS:
   - Dominance Score of a Subarray:
     The highest frequency of any single element in that subarray (i.e., mode count).
     e.g., in [2, 3, 2], '2' appears 2 times, '3' appears 1 time -> Dominance Score = 2.
   - Goal:
     Divide the array into exactly K contiguous, non-empty subarrays to minimize (or maximize)
     the sum of the dominance scores across all K subarrays.

----------------------------------------------------------------------------------------
2. DP STATE DEFINITION:
   dp[k][i] represents:
   - The optimal (min/max) total dominance score of partitioning the FIRST 'i' elements
     (arr[0 ... i-1]) into exactly 'k' non-empty contiguous subarrays.

   Table dimensions: (K + 1) x (n + 1)
   - k goes from 0 to K (number of subarrays).
   - i goes from 0 to n (number of elements considered so far).

----------------------------------------------------------------------------------------
3. DIFFERENCE BETWEEN (k, i) AND (k - 1, j):
   Line: dp[k][i] = Math.max(dp[k][i], dp[k - 1][j] + maxFreq);

   Think of this as splitting the first 'i' elements into TWO PARTS at cut point 'j':

   Array: [ arr[0], arr[1], ... arr[j - 1] ]  |  [ arr[j], arr[j+1], ... arr[i - 1] ]
          \________________________________/     \_________________________________/
                       PART 1                                  PART 2
               Covered by dp[k - 1][j]                   Covered by maxFreq

   - dp[k][i] (indices k and i):
     * The target state we are computing right now.
     * 'i' = considers the first 'i' elements total (indices 0 to i - 1).
     * 'k' = partitioned into 'k' subarrays total.

   - dp[k - 1][j] (indices k - 1 and j):
     * The subproblem already computed in the previous row.
     * 'j' = considers the first 'j' elements (indices 0 to j - 1).
     * 'k - 1' = partitioned into 'k - 1' subarrays (leaving 1 subarray to be formed).

   - maxFreq:
     * Dominance score of the single new k-th subarray: arr[j ... i - 1].

   - Combined Transition:
     dp[k][i] = optimal over all valid j { dp[k - 1][j] + maxFreq(arr[j ... i - 1]) }

----------------------------------------------------------------------------------------
4. WHY ITERATE 'j' BACKWARDS (from i - 1 down to k - 1)?
   - When j goes backwards (i - 1, i - 2, ..., k - 1), we are expanding the k-th subarray
     to the left by one element (arr[j]) in each step.
   - This allows updating 'freqMap' and 'maxFreq' in O(1) time per step without recomputing
     the entire subarray's frequency map from scratch.

----------------------------------------------------------------------------------------
5. BASE CASES & OVERFLOW PREVENTION:
   - dp[0][0] = 0 (0 elements partitioned into 0 subarrays costs 0).
   - For getMinDominanceScore, dp table is filled with Integer.MAX_VALUE / 2.
     We divide by 2 so that adding 'maxFreq' to dp[k - 1][j] does not trigger a 32-bit
     integer overflow into negative numbers.

----------------------------------------------------------------------------------------
6. COMPLEXITY:
   - Time Complexity:  O(K * n^2)
     Three nested loops: k from 1 to K, i from k to n, j from i - 1 down to k - 1.
   - Space Complexity: O(K * n) for the DP table + O(n) for the frequency map.
========================================================================================
*/

