package utils;

import javax.swing.tree.ExpandVetoException;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Comparator.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class indexing {
    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter("bin/index.csv", "UTF-8");

        for (int size = 50; size < 201; size += 25) {
            Map<String, Integer> map = new TreeMap<String,Integer>();
            System.out.println("Doing " + size);
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\DELL\\Desktop\\testP\\testP\\bin\\server-20.log"));
            try {
                String line = br.readLine();

                while (line != null) {
                    line = line.replaceAll("[^a-zA-Z0-9 ]", " ").replaceAll(" +", " ").trim();
                    for (int i = 0; i + size < line.length(); i++) {
                        int s = -1;
                        try {
                            s = map.get(line.substring(i, i + size));
                        } catch (Exception e) {

                        }
                        if (s == -1) {
                            map.put(line.substring(i, i + size), 1);
                        } else {
                            int count = map.get(line.substring(i, i + size));
                            map.put(line.substring(i, i + size), ++count);
                        }
                    }
                    line = br.readLine();
                }
            } finally {
                br.close();
//                writer.println("size: " + size);
//                LinkedHashMap<Integer, String> sortedMap =
//                        map.entrySet().stream().
//                                sorted(Map.Entry.comparingByValue()).
//                                collect(Collectors.toMap(Entry::getKey, Map.Entry::getValue,
//                                        (e1, e2) -> e1, LinkedHashMap::new));
                int count = 0;

//                for (Map.Entry<String, Integer> e : s) {
//                    if (count < 10) {
//                        writer.println(e.getKey() + "," + e.getValue());
//                        count++;
//                    } else break;
//                }
                writer.println("-------------------------------------------------------------");
            }
        }

        writer.close();

    }
    static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? 1 : res;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
