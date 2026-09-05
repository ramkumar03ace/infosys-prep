import java.util.Arrays;
import java.util.Scanner;

public class q5 {

    /**
     * Finds the maximum number of consecutive vacation days Andy can take
     * after canceling at most K obligations.
     *
     * @param n Total number of days (1 to n)
     * @param m Number of scheduled obligations
     * @param k Maximum number of obligations that can be canceled
     * @param d Array of days on which obligations are scheduled
     * @return Maximum consecutive vacation days
     */
    public static long getMaxVacationDays(long n, int m, int k, long[] d) {
        // If there are no obligations, or if we can cancel all of them,
        // Andy can take the entire N days as vacation.
        if (m == 0 || k >= m) {
            return n;
        }

        // Sort the obligation days in ascending order
        Arrays.sort(d);

        // Create an extended array with virtual boundary obligations:
        // - arr[0] = 0         (boundary before day 1)
        // - arr[m + 1] = n + 1 (boundary after day n)
        long[] arr = new long[m + 2];
        arr[0] = 0;
        for (int i = 0; i < m; i++) {
            arr[i + 1] = d[i];
        }
        arr[m + 1] = n + 1;

        long maxVacation = 0;

        // If we cancel k obligations from index j to (j + k - 1):
        // - The previous non-canceled obligation is at arr[j - 1]
        // - The next non-canceled obligation is at arr[j + k]
        //
        // Vacation starts at: arr[j - 1] + 1
        // Vacation ends at:   arr[j + k] - 1
        // Total days = (arr[j + k] - 1) - (arr[j - 1] + 1) + 1
        //            = arr[j + k] - arr[j - 1] - 1
        for (int j = 1; j <= m - k + 1; j++) {
            long vacationDays = arr[j + k] - arr[j - 1] - 1;
            maxVacation = Math.max(maxVacation, vacationDays);
        }

        return maxVacation;
    }

    public static void main(String[] args) {
        // Test Case 1:
        // N = 10 days, M = 3 obligations on days [3, 6, 8], K = 1 cancel allowed
        // If we cancel day 3: vacation from day 1 to 5 = 5 days
        // If we cancel day 6: vacation from day 4 to 7 = 4 days
        // If we cancel day 8: vacation from day 7 to 10 = 4 days
        // Max = 5 days
        long n1 = 10;
        int m1 = 3;
        int k1 = 1;
        long[] d1 = { 3, 6, 8 };
        System.out.println("Test Case 1 (N=10, M=3, K=1, D=[3, 6, 8]): " + getMaxVacationDays(n1, m1, k1, d1));

        // Test Case 2:
        // K >= M: Andy can cancel all obligations -> gets all N days
        long n2 = 15;
        int m2 = 2;
        int k2 = 2;
        long[] d2 = { 5, 10 };
        System.out.println("Test Case 2 (K >= M): " + getMaxVacationDays(n2, m2, k2, d2));

        // Test Case 3:
        // K = 0 (No cancellations allowed)
        // D = [4, 7] -> intervals are [1..3] (3 days), [5..6] (2 days), [8..10] (3 days) -> Max = 3
        long n3 = 10;
        int m3 = 2;
        int k3 = 0;
        long[] d3 = { 4, 7 };
        System.out.println("Test Case 3 (K=0): " + getMaxVacationDays(n3, m3, k3, d3));

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
                                LOGIC & EXPLANATION
========================================================================================

1. PROBLEM SUMMARY:
   - There are N days numbered 1 to N.
   - Andy has M obligations on days D[0], D[1], ..., D[M - 1].
   - Andy can cancel at most K obligations.
   - We want to find the MAXIMUM number of CONSECUTIVE vacation days Andy can take.

----------------------------------------------------------------------------------------
2. THE BOUNDARY ARRAY TECHNIQUE:
   To get the longest vacation, Andy should choose a block of K consecutive obligations
   and cancel all of them.

   Add two virtual boundary obligations:
   - arr[0] = 0 (an obligation just before day 1)
   - arr[M + 1] = N + 1 (an obligation just after day N)

   Now the sorted array of obligations is:
     arr = [0, D[0], D[1], ..., D[M - 1], N + 1]

----------------------------------------------------------------------------------------
3. SLIDING WINDOW OF SIZE K:
   Suppose Andy cancels K obligations starting from index j to (j + k - 1):
   - The last obligation BEFORE his vacation is at arr[j - 1].
     So his vacation can start at day: arr[j - 1] + 1.
   - The first obligation AFTER his vacation is at arr[j + k].
     So his vacation can end at day: arr[j + k] - 1.

   Total vacation days for this choice:
     Days = (arr[j + k] - 1) - (arr[j - 1] + 1) + 1
          = arr[j + k] - arr[j - 1] - 1

   We slide this window of K canceled obligations from j = 1 to (M - K + 1)
   and take the maximum!

----------------------------------------------------------------------------------------
4. EDGE CASES:
   - M == 0 or K >= M:
     Andy can cancel all obligations, meaning he is free every day from 1 to N.
     Result = N.
   - K == 0:
     Formula still holds: arr[j] - arr[j - 1] - 1 (the maximum gap between any two obligations).

----------------------------------------------------------------------------------------
5. COMPLEXITY:
   - Time Complexity:  O(M log M) to sort the obligations + O(M) to slide the window.
                       Overall: O(M log M).
   - Space Complexity: O(M) for the boundary array.
========================================================================================
*/
