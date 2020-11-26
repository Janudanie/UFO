package cphbusiness.ufo.letterfrequencies;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class FasterHDTryMergeMapBufferedread {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Timer t = new Timer();
        double timeSets[] = new double[50];
        for(int xx = 0 ; xx<50;xx++ ) {

            for (int x = 0; x < 50; x++) {
                t.play();

                String fileName = "C:\\Temp\\FoundationSeries.txt\\";
                Reader reader = new FileReader(fileName);
                FileReader fr = new FileReader(fileName);
                BufferedReader breader = new BufferedReader(fr);
                Map<Integer, Long> freq = new HashMap<>();
                tallyChars(breader, freq);
                //print_tally(freq);
                timeSets[x] = t.check();
            }
       /* for(int y = 0;y<50;y++){
            System.out.println((int)timeSets[y]);
        }*/
            NumberFormat formatter = new DecimalFormat("##.00");

            System.out.print("mean is : " + formatter.format(t.getMean()) + " ms");
            System.out.println(" +/-  " +formatter.format( t.getSDev()) + " ms");
        }
    }

    private static void tallyChars(BufferedReader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            freq.merge(b,1L,Long::sum);
        }
    }

    private static void print_tally(Map<Integer, Long> freq) {
        int dist = 'a' - 'A';
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) {
            upperAndlower.put(c, freq.getOrDefault(c, 0L) + freq.getOrDefault(c + dist, 0L));
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Character c : sorted.keySet()) {
            System.out.println("" + c + ": " + sorted.get(c));;
        }
    }
}
