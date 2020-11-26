package cphbusiness.ufo.letterfrequencies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class TryMergeMap {

    public static void main(String[] args) throws FileNotFoundException, IOException {
    Timer t = new Timer();
    double timeSets[] = new double[50];


    for(int x = 0; x<50;x++) {
        t.play();

        String fileName = "src/main/resources/FoundationSeries.txt";
        Reader reader = new FileReader(fileName);
        Map<Integer, Long> freq = new HashMap<>();
        tallyChars(reader, freq);
        //print_tally(freq);
        timeSets[x] = t.check();
    }
        for(int y = 0;y<50;y++){
            System.out.println((int)timeSets[y]);
        }
}

    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
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
