import java.util.Arrays;
import java.util.Scanner;

public class q5 {

    /**
     * Finds the maximum number of consecutive vacation days Andy can take
     * after canceling at most K obligations.
     *
     * Two-Pointer (L and R) Window Concept:
     * - [L, R] represents the window of K canceled obligations.
     * - Andy cancels all obligations from index L to index R (size = K).
     * - His vacation starts the day after obligation (L - 1), or day 1 if L == 0.
     * - His vacation ends the day before obligation (R + 1), or day N if R == M - 1.
     *
     * @param n Total number of days (1 to n)
     * @param m Number of scheduled obligations
     * @param k Maximum number of obligations that can be canceled
     * @param d Array of days on which obligations are scheduled
     * @return Maximum consecutive vacation days
     */
    public static long getMaxVacationDays(long n, int m, int k, long[] d) {
        // Edge Case 1: No obligations or can cancel all of them -> take all N days
        if (m == 0 || k >= m) {
            return n;
        }

        // Sort the obligation days in ascending order
        Arrays.sort(d);

        // Edge Case 2: Cannot cancel any obligations (K = 0)
        // Check the gaps between obligations directly
        if (k == 0) {
            long maxVacation = d[0] - 1; // Gap before first obligation
            for (int i = 1; i < m; i++) {
                maxVacation = Math.max(maxVacation, d[i] - d[i - 1] - 1);
            }
            maxVacation = Math.max(maxVacation, n - d[m - 1]); // Gap after last obligation
            return maxVacation;
        }

        long maxVacation = 0;

        // TWO POINTERS: L and R
        // Window [L, R] holds exactly K canceled obligations (R - L + 1 == K)
        for (int R = k - 1; R < m; R++) {
            int L = R - k + 1; // Left pointer

            // The uncancelled obligation to the left (or Day 0 if none)
            long leftBound = (L > 0) ? d[L - 1] : 0;

            // The uncancelled obligation to the right (or Day n + 1 if none)
            long rightBound = (R < m - 1) ? d[R + 1] : n + 1;

            // Free days strictly between two obligations A and B is simply: B - A - 1
            long vacationDays = rightBound - leftBound - 1;
            maxVacation = Math.max(maxVacation, vacationDays);
        }

        return maxVacation;
    }

    public static void main(String[] args) {
        // Test Case 1:
        // N = 10 days, M = 3 obligations at [3, 6, 8], K = 1 cancel allowed
        //
        // L=0, R=0 (cancel d[0]=3): start = 1, end = d[1]-1 = 5  -> 5 days (1 to 5)
        // L=1, R=1 (cancel d[1]=6): start = d[0]+1 = 4, end = d[2]-1 = 7 -> 4 days (4 to 7)
        // L=2, R=2 (cancel d[2]=8): start = d[1]+1 = 7, end = 10 -> 4 days (7 to 10)
        // Max = 5 days
        long n1 = 10;
        int m1 = 3;
        int k1 = 1;
        long[] d1 = { 3, 6, 8 };
        System.out.println("Test Case 1 (N=10, K=1, D=[3, 6, 8]): " + getMaxVacationDays(n1, m1, k1, d1));

        // Test Case 2:
        // N = 15, M = 4, K = 2, D = [3, 6, 9, 12]
        // L=0, R=1 (cancel 3, 6):  start=1, end=8   -> 8 days
        // L=1, R=2 (cancel 6, 9):  start=4, end=11  -> 8 days
        // L=2, R=3 (cancel 9, 12): start=7, end=15  -> 9 days (Max)
        long n2 = 15;
        int m2 = 4;
        int k2 = 2;
        long[] d2 = { 3, 6, 9, 12 };
        System.out.println("Test Case 2 (N=15, K=2, D=[3, 6, 9, 12]): " + getMaxVacationDays(n2, m2, k2, d2));

        // Test Case 3:
        // K = 0 (No cancellations allowed)
        long n3 = 10;
        int m3 = 2;
        int k3 = 0;
        long[] d3 = { 4, 7 };
        System.out.println("Test Case 3 (K=0, D=[4, 7]): " + getMaxVacationDays(n3, m3, k3, d3));

        /*
         * Interactive input reading for online assessments:
         *
         * Scanner sc = new Scanner(System.in);
         * if (sc.hasNextLong()) {
         *     long N = sc.nextLong();
         *     int M = sc.nextInt();
         *     int K = sc.nextInt();
         *     long[] D = new long[M];
         *     for (int i = 0; i < M; i++) {
         *         D[i] = sc.nextLong();
         *     }
         *     System.out.println(getMaxVacationDays(N, M, K, D));
         * }
         * sc.close();
         */
    }
}

/*
========================================================================================
                          INTUITIVE L & R POINTER EXPLANATION
========================================================================================

1. CORE CONCEPT:
   Instead of modifying arrays or shifting indices:
   - 'L' is the index of the first obligation Andy cancels.
   - 'R' is the index of the last obligation Andy cancels.
   - The window [L, R] has size: R - L + 1 = K obligations.

----------------------------------------------------------------------------------------
2. WHERE DOES THE VACATION START AND END?
   Since all obligations from d[L] to d[R] are canceled:

   - Left Boundary (startDay):
     * If L == 0: There are no obligations before L, so Andy can start on Day 1.
     * Otherwise: The last non-canceled obligation was on day d[L - 1].
                  Andy's vacation starts the very next day: d[L - 1] + 1.

   - Right Boundary (endDay):
     * If R == M - 1: There are no obligations after R, so Andy can vacation until Day N.
     * Otherwise: The next obligation is on day d[R + 1].
                  Andy's vacation must end the day before: d[R + 1] - 1.

   - Consecutive Vacation Days:
     Vacation = endDay - startDay + 1

----------------------------------------------------------------------------------------
3. SLIDING THE WINDOW:
   We slide R from (K - 1) to (M - 1), with L = R - K + 1.
   Each step takes O(1) time.
   Total time: O(M log M) to sort + O(M) to slide = O(M log M).
========================================================================================
*/
