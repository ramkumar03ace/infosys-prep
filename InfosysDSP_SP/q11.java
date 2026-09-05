import java.util.*;

public class q11 {
    public static void main(String[] args) {
        // Sample ArrayList<ArrayList<Integer>> representing pairs/rows: [[5, 100], [2, 400], [9, 200], [1, 300]]
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        list.add(new ArrayList<>(Arrays.asList(5, 100)));
        list.add(new ArrayList<>(Arrays.asList(2, 400)));
        list.add(new ArrayList<>(Arrays.asList(9, 200)));
        list.add(new ArrayList<>(Arrays.asList(1, 300)));

        System.out.println("Original ArrayList<ArrayList<Integer>>: " + list);

        // =========================================================================
        // STEP 1: Sort 2D ArrayList based on index 0 (row.get(0))
        // =========================================================================
        // Using Lambda expression: compares element at index 0 of list 'a' vs list 'b'
        list.sort((a, b) -> Integer.compare(a.get(0), b.get(0)));

        // Alternative way using Comparator:
        // Collections.sort(list, Comparator.comparingInt(a -> a.get(0)));

        System.out.println("Sorted ArrayList<ArrayList<Integer>>:   " + list);

        // =========================================================================
        // STEP 2: Separate index 0 and index 1 into separate ArrayLists
        // =========================================================================
        ArrayList<Integer> keys = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();

        for (ArrayList<Integer> row : list) {
            keys.add(row.get(0));   // First value (index 0)
            values.add(row.get(1)); // Second value (index 1)
        }

        System.out.println("\nSeparated Keys ArrayList (index 0):   " + keys);
        System.out.println("Separated Values ArrayList (index 1): " + values);

        /*
         * =========================================================================
         * REFERENCE (Previous Map/TreeMap code for reference)
         * =========================================================================
         * Map<Integer, Integer> map = new HashMap<>();
         * 
         * Map<Integer, Integer> treeMap = new TreeMap<>(map);
         * ArrayList<Integer> keys1 = new ArrayList<>(treeMap.keySet());
         * ArrayList<Integer> values1 = new ArrayList<>(treeMap.values());
         * 
         * ArrayList<Integer> keys2 = new ArrayList<>(map.keySet());
         * Collections.sort(keys2);
         * ArrayList<Integer> values2 = new ArrayList<>();
         * for (int key : keys2) {
         *     values2.add(map.get(key));
         * }
         */
    }
}
