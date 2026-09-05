import java.util.*;

public class q6 {

    /**
     * Calculates the contribution of a cooling unit given its heat load and the critical threshold.
     *
     * In Turbo Mode (heatLoad >= critical):
     * The unit contributes a boosted heat reduction (default: 2 * heatLoad).
     *
     * In Normal Mode (heatLoad < critical):
     * The unit contributes its standard heat load.
     */
    public static long getContribution(long heatLoad, long critical) {
        if (heatLoad >= critical) {
            return 2 * heatLoad; // Turbo mode: 2x contribution
        }
        return heatLoad; // Normal mode: 1x contribution
    }

    /**
     * Solves the cooling system operations problem.
     *
     * @param n           Number of time intervals
     * @param d           Minimum separation between selected cooling units
     * @param initialHeat Initial heat loads for intervals 0 to n - 1
     * @param operations  List of operations (either update or query)
     * @return Sum of results of all query operations
     */
    public static long solveCoolingSystem(int n, int d, long[] initialHeat, List<long[]> operations) {
        // Current heat loads array
        long[] heat = Arrays.copyOf(initialHeat, n);

        long totalSumOfQueries = 0;

        for (long[] op : operations) {
            int type = (int) op[0];

            if (type == 1) {
                // Update Operation: 1 position value
                int pos = (int) op[1];
                long value = op[2];
                heat[pos] = value;
            } else if (type == 2) {
                // Query Operation: 2 l r k critical
                int l = (int) op[1];
                int r = (int) op[2];
                int k = (int) op[3];
                long critical = op[4];

                // 1. Collect all candidates in range [l, r]
                List<Unit> candidates = new ArrayList<>();
                for (int i = l; i <= r; i++) {
                    candidates.add(new Unit(i, heat[i]));
                }

                // 2. Greedily sort in descending order of heat load
                //    Ties broken by index ascending for stability
                candidates.sort((a, b) -> {
                    if (b.heatLoad != a.heatLoad) {
                        return Long.compare(b.heatLoad, a.heatLoad);
                    }
                    return Integer.compare(a.index, b.index);
                });

                // 3. Greedily pick at most k units that are at least d intervals apart
                TreeSet<Integer> selectedIndices = new TreeSet<>();
                long queryHeatReduction = 0;

                for (Unit unit : candidates) {
                    if (selectedIndices.size() == k) {
                        break;
                    }

                    // Check separation constraint:
                    // Must be at least d units away from any previously selected unit
                    Integer prev = selectedIndices.floor(unit.index);
                    if (prev != null && (unit.index - prev) < d) {
                        continue;
                    }

                    Integer next = selectedIndices.ceiling(unit.index);
                    if (next != null && (next - unit.index) < d) {
                        continue;
                    }

                    // Valid selection
                    selectedIndices.add(unit.index);
                    queryHeatReduction += getContribution(unit.heatLoad, critical);
                }

                totalSumOfQueries += queryHeatReduction;
            }
        }

        return totalSumOfQueries;
    }

    // Helper class to store interval index and its current heat load
    static class Unit {
        int index;
        long heatLoad;

        Unit(int index, long heatLoad) {
            this.index = index;
            this.heatLoad = heatLoad;
        }
    }

    public static void main(String[] args) {
        // Sample Test Demonstration
        int n = 6;
        int d = 2; // Selected units must be at least 2 indices apart (e.g. idx 0 and 2, but not 0 and 1)
        long[] initialHeat = { 10, 50, 30, 20, 40, 15 };

        List<long[]> operations = new ArrayList<>();
        // Query 1: range [0, 5], pick at most 2 units, critical = 35
        // Candidates sorted: 50 (idx 1), 40 (idx 4), 30 (idx 2), 20 (idx 3), 15 (idx 5), 10 (idx 0)
        // Pick 50 (idx 1) -> enters turbo mode (50 >= 35) -> contributes 2 * 50 = 100
        // Next pick: 40 (idx 4) -> valid (|4 - 1| = 3 >= 2) -> turbo mode (40 >= 35) -> contributes 2 * 40 = 80
        // Query result = 100 + 80 = 180
        operations.add(new long[] { 2, 0, 5, 2, 35 });

        // Update 1: update position 1 to 25
        operations.add(new long[] { 1, 1, 25 });

        // Query 2: range [0, 3], pick at most 2 units, critical = 25
        // Range heat: idx 0: 10, idx 1: 25, idx 2: 30, idx 3: 20
        // Pick 30 (idx 2) -> turbo mode (30 >= 25) -> contributes 60
        // Next valid: idx 0 (dist |2 - 0| = 2 >= 2) -> normal mode (10 < 25) -> contributes 10
        // Query result = 60 + 10 = 70
        operations.add(new long[] { 2, 0, 3, 2, 25 });

        long totalSum = solveCoolingSystem(n, d, initialHeat, operations);
        System.out.println("Total Sum of all queries: " + totalSum); // Expected: 180 + 70 = 250

        /*
         * Interactive Scanner template for online assessment platforms:
         *
         * Scanner sc = new Scanner(System.in);
         * if (sc.hasNextInt()) {
         *     int N = sc.nextInt();
         *     int D = sc.nextInt();
         *     long[] heat = new long[N];
         *     for (int i = 0; i < N; i++) {
         *         heat[i] = sc.nextLong();
         *     }
         *     int Q = sc.nextInt();
         *     List<long[]> ops = new ArrayList<>();
         *     for (int i = 0; i < Q; i++) {
         *         int type = sc.nextInt();
         *         if (type == 1) {
         *             int pos = sc.nextInt();
         *             long val = sc.nextLong();
         *             ops.add(new long[] { 1, pos, val });
         *         } else {
         *             int l = sc.nextInt();
         *             int r = sc.nextInt();
         *             int k = sc.nextInt();
         *             long critical = sc.nextLong();
         *             ops.add(new long[] { 2, l, r, k, critical });
         *         }
         *     }
         *     System.out.println(solveCoolingSystem(N, D, heat, ops));
         * }
         * sc.close();
         */
    }
}

/*
========================================================================================
                                LOGIC & EXPLANATION
========================================================================================

1. PROBLEM OVERVIEW:
   - There are n time intervals (0 to n - 1), each with an initial heat load.
   - Selected units in any query must be at least 'd' indices apart (|i - j| >= d).
   - Operation 1 (Update): Update heat load at 'position' to 'value'.
   - Operation 2 (Query):
     * Within range [l, r], select at most 'k' cooling units.
     * Intervals are greedily selected in descending order of heat load.
     * If heat load >= critical -> Turbo Mode.
     * Else -> Normal Mode.
   - Output: The sum of results across ALL query operations.

----------------------------------------------------------------------------------------
2. GREEDY SELECTION MECHANISM:
   For each query [l, r, k, critical]:
   - Extract intervals in [l, r] into a candidate list.
   - Sort candidates primarily by heat load descending.
   - Iterate through sorted candidates:
     * Check if candidate index is at least 'd' apart from already selected indices.
     * We use a TreeSet<Integer> to quickly check floor() and ceiling() in O(log k) time.
     * If valid, select this unit and add its contribution (turbo or normal).
     * Stop once 'k' units are selected or candidates exhausted.

----------------------------------------------------------------------------------------
3. TURBO VS NORMAL MODE CONTRIBUTION:
   - Turbo Mode (heat >= critical): Contributes 2 * heat load (boosted cooling).
   - Normal Mode (heat < critical): Contributes 1 * heat load.

----------------------------------------------------------------------------------------
4. COMPLEXITY:
   - Update Operation: O(1) direct array assignment.
   - Query Operation:  O(L log L) to sort candidates in range, where L = (r - l + 1) <= n.
                       O(L log k) to greedily validate with TreeSet.
   - Total Space: O(n) for the heat load array.
========================================================================================
*/
