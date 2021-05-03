package utils;

import java.util.Arrays;
import java.util.Random;

public class randomLoto {
    static int numberOfIterations = 10;

    public static void main(String[] args) {
        Random r = new Random(11 * 6);
        int[][] arrForAll = new int[numberOfIterations][6];
        for (int j = 0; j < numberOfIterations; j++) {
            int[] arr = new int[6];
            for (int i = 0; i < 6; i++) {
                int n = r.nextInt(37) + 1;
                if (numberNotInArr(n, arr)) {
                    arr[i] = n;
                } else {
                    i--;
                }
            }
            Arrays.sort(arr);
            System.out.println(Arrays.toString(arr));
            arrForAll[j] = arr;
        }
        System.out.println("FUF");


    }

    private static boolean numberNotInArr(int n, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == n) {
                return false;
            }
        }
        return true;
    }
}
