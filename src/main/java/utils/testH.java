package utils;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


class Solution {
    public static void main(String[] args) {
//        System.out.println(solution("axxaxa"));
        int N = -666;
        int max = N;
        char d = 0;
        if (N < 0) {
            d = '-';
            max = 10 * N;
            N = (-1 * N);
        }
        String s = N + "";
        char[] c = s.toCharArray();
        for (int i = 0; i <= c.length; i++) {
            int flag = 0;
            char[] tempC = new char[c.length + 1];
            for (int j = 0; j < tempC.length; j++) {
                if (j == i) {
                    tempC[j] = '5';
                    flag = -1;
                } else {
                    tempC[j] = c[j + flag];
                }
            }
            String tempS = "";
            for (int j = 0; j < tempC.length; j++) {
                tempS += tempC[j];
            }
            if (d == '-') {
                tempS = "-" + tempS;
            }
            int temp = Integer.parseInt(tempS);
            if (temp > max) {
                max = temp;
            }
        }
        System.out.println(max);
    }

    public static int solution(String S) {

        char[] chars = S.toCharArray();
        int sum = 0;
        System.out.println("The no of characters are " + chars);
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (char count : chars) {
            if (map.containsKey(count)) {
                map.put(count, map.get(count) + 1);
            } else {
                map.put(count, 1);
            }
        }
        System.out.println("The number of characters are " + map);
        Set<Map.Entry<Character, Integer>> ch = map.entrySet();

        for (Map.Entry<Character, Integer> e : ch) {
            if ((e.getValue()) % 2 == 0) {

            } else {
                sum++;
            }
        }

        System.out.println("The value of sum is " + sum);
        return sum;
    }

}