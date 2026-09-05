import java.util.ArrayList;
import java.util.Scanner;

public class q5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int currLev = sc.nextInt();
        int ans = 0;

        int[] p = new int[n];
        int[] b = new int[n];

        for (int i = 0; i < n; i++) {
            p[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            b[i] = sc.nextInt();
        }

        ArrayList<int[]> arr = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            arr.add(new int[] { p[i], b[i] });
        }

        arr.sort((x, y) -> Integer.compare(x[0], y[0]));

        for (int[] pair : arr) {
            System.out.println("p: " + pair[0] + ", b: " + pair[1]);
        }

        for (int i = 0; i < n; i++) {
            if (currLev >= arr.get(i)[0]) {
                currLev += arr.get(i)[1];
                ans += 1;
            } else {
                break;
            }
        }
        System.out.println(ans);
        sc.close();
    }
}
