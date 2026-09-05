public class q4 {

    /**
     * Solves the Paint House problem with budget constraint using a 2D DP table.
     *
     * @param n    Number of houses
     * @param c    Number of available colors
     * @param b    Maximum allowed budget
     * @param cost An n x c matrix where cost[i][j] is the cost to paint house i
     *             with color j
     * @return The minimum painting cost if <= b, otherwise -1
     */
    public static long getMinPaintingCost(int n, int c, long b, int[][] cost) {
        if (n == 0) {
            return 0 <= b ? 0 : -1;
        }

        // If there are 2 or more houses but only 1 color,
        // adjacent houses are forced to have the same color -> Impossible
        if (n > 1 && c < 2) {
            return -1;
        }

        // Step 1: Create 2D DP array of size n x c
        // dp[i][j] = minimum cost to paint houses 0 to i,
        // such that house i is painted with color j.
        long[][] dp = new long[n][c];

        // Step 2: For House 0 (first house), store the raw painting costs
        for (int j = 0; j < c; j++) {
            dp[0][j] = cost[0][j];
        }

        // Step 3: Fill the DP table for House 1 to House n - 1
        for (int i = 1; i < n; i++) {
            // If we choose to paint house i with color j:
            for (int j = 0; j < c; j++) {
                // Find the minimum cost of painting previous house (i - 1)
                // using ANY OTHER color k (where k != j)
                long minPrev = Long.MAX_VALUE;
                for (int k = 0; k < c; k++) {
                    if (k != j) {
                        minPrev = Math.min(minPrev, dp[i - 1][k]);
                    }
                }

                // Current cost = painting house i with color j + best valid previous house cost
                dp[i][j] = cost[i][j] + minPrev;
            }
        }

        // Step 4: Find the minimum total cost from the last house (n - 1)
        long minTotalCost = Long.MAX_VALUE;
        for (int j = 0; j < c; j++) {
            minTotalCost = Math.min(minTotalCost, dp[n - 1][j]);
        }

        // Step 5: Budget check
        return (minTotalCost <= b) ? minTotalCost : -1;
    }

    public static void main(String[] args) {
        // Example: 3 houses, 3 colors, Budget = 20
        // House 0: [1, 5, 7]
        // House 1: [5, 8, 4]
        // House 2: [3, 2, 9]
        int n = 3;
        int c = 3;
        long b = 20;
        int[][] cost = {
                { 1, 5, 7 },
                { 5, 8, 4 },
                { 3, 2, 9 }
        };

        System.out.println("Minimum Total Cost: " + getMinPaintingCost(n, c, b, cost));

        // When budget is too low (Budget = 5, but min cost is 7):
        System.out.println("Budget = 5: " + getMinPaintingCost(n, c, 5, cost));

        /*
         * To read from standard input (as specified in problem statement):
         *
         * Scanner sc = new Scanner(System.in);
         * int N = sc.nextInt();
         * int C = sc.nextInt();
         * long B = sc.nextLong();
         * int[][] cost = new int[N][C];
         * for (int i = 0; i < N; i++) {
         * for (int j = 0; j < C; j++) {
         * cost[i][j] = sc.nextInt();
         * }
         * }
         * System.out.println(getMinPaintingCost(N, C, B, cost));
         * sc.close();
         */
    }
}

/*
 * =============================================================================
 * ===========
 * EXPLANATION OF THE 2D DP APPROACH
 * =============================================================================
 * ===========
 * 
 * 1. IS THIS APPROACH CORRECT?
 * YES, 100% CORRECT! This is the most intuitive and standard way to solve
 * the Paint House problem.
 * 
 * -----------------------------------------------------------------------------
 * -----------
 * 2. HOW IT WORKS STEP-BY-STEP:
 * - Create a 2D array `dp[n][c]`.
 * - dp[i][j] means:
 * "The minimum cost to paint houses 0 to i, given that house i is painted with color j."
 * 
 * - House 0 (First House):
 * There is no previous house, so we just copy the raw painting costs:
 * dp[0][0] = cost[0][0]
 * dp[0][1] = cost[0][1]
 * dp[0][2] = cost[0][2]
 * ...
 * 
 * - House i (From House 1 to n - 1):
 * If we paint house i with color j, the previous house (i - 1) CANNOT be color
 * j.
 * So we look at house (i - 1) and find the minimum of all OTHER colors k != j:
 * dp[i][j] = cost[i][j] + min(dp[i - 1][k] for all k != j)
 * 
 * -----------------------------------------------------------------------------
 * -----------
 * 3. SPECIAL CASE FOR 3 COLORS (c = 3):
 * When there are only 3 colors (0, 1, 2), you don't even need the inner k loop!
 * It simplifies directly to:
 * dp[i][0] = cost[i][0] + Math.min(dp[i - 1][1], dp[i - 1][2]); // min of other
 * 2
 * dp[i][1] = cost[i][1] + Math.min(dp[i - 1][0], dp[i - 1][2]); // min of other
 * 2
 * dp[i][2] = cost[i][2] + Math.min(dp[i - 1][0], dp[i - 1][1]); // min of other
 * 2
 * 
 * For general C colors (which the problem statement specifies), we use:
 * for (int k = 0; k < c; k++) {
 * if (k != j) minPrev = Math.min(minPrev, dp[i - 1][k]);
 * }
 * 
 * -----------------------------------------------------------------------------
 * -----------
 * 4. FINAL ANSWER & BUDGET CHECK:
 * - The answer is the minimum value in the last row: min(dp[n - 1][0], ...,
 * dp[n - 1][c - 1]).
 * - If this minimum <= Budget B, output the minimum.
 * - Otherwise, output -1.
 * =============================================================================
 * ===========
 */
