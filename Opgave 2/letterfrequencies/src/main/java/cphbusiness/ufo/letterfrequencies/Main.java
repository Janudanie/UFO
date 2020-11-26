package cphbusiness.ufo.letterfrequencies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.toMap;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */
public class Main {


    public static void main(String[] args) throws FileNotFoundException, IOException {
        Timer t = new Timer();
        double timeSets[] = new double[50];

        for(int xx = 0 ; xx < 50 ; xx++) {

            for (int x = 0; x < 50; x++) {

                String fileName = "src/main/resources/FoundationSeries.txt";
                Reader reader = new FileReader(fileName);
                Map<Integer, Long> freq = new HashMap<>();
                t.play();
                tallyChars(reader, freq);
                timeSets[x] = t.check();
                //print_tally(freq);

            }
        /*for(int y = 0;y<50;y++){
            System.out.println((int)timeSets[y]);
        }*/

            NumberFormat formatter = new DecimalFormat("##.00");

            System.out.print("mean is : " + formatter.format(t.getMean()) + " MS");
            System.out.println(" +/-  " +formatter.format( t.getSDev()) + " MS");
        }

    }

    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            try {
                freq.put(b, freq.get(b) + 1);

            } catch (NullPointerException np) {
                freq.put(b, 1L);
            };
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