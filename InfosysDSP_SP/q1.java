// remove 1 occ of a didgit in string so that the resulting string has max no value.

import java.util.*;

class q1 {
    public static void main(String args[]) {
        String n = "1321";
        String d = "1";
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) == d.charAt(0)) {
                arr.add(n.substring(0, i) + n.substring(i + 1));
            }
        }
        System.out.println(Collections.max(arr));
    }
}