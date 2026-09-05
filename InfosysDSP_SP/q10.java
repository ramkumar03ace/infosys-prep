
import java.util.*;

public class q10 {
    public static int shiftCards(ArrayList<String> cards, int s, String target) {
        int n = cards.size();
        int e = cards.indexOf(target);

        if (e == -1) {
            return -1; // Target card not found in list
        }

        if (s == e) {
            return 0;
        }

        if (Math.abs(s - e) == 1) {
            return 1;
        }

        if (s < e) {
            int forwardCount = e - s;
            int backwardCount = s + (n - e);
            return Math.min(forwardCount, backwardCount);
        }

        if (e < s) {
            int forwardCount = s - e;
            int backwardCount = e + (n - s);
            return Math.min(forwardCount, backwardCount);
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // int n = sc.nextInt();
        sc.nextLine(); // Consume the leftover newline ('\n') from sc.nextInt()

        ArrayList<String> cards = new ArrayList<>(Arrays.asList(sc.nextLine().trim().split("\\s+")));
        System.out.println("Cards read: " + cards);
        int s = sc.nextInt();

        String target = sc.next();

        int result = shiftCards(cards, s, target);

        System.out.println("\nMinimum shifts required: " + result);

        sc.close();
    }
}
