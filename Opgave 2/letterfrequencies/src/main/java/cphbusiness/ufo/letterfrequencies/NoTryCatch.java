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

public class NoTryCatch {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        double timeSets[] = new double[50];
        Timer t = new Timer();

        for(int x =0 ;x<50;x++) {

            String fileName = "src/main/resources/FoundationSeries.txt"; // Big O = 1
            Reader reader = new FileReader(fileName); //Big O = 1
            Map<Integer, Long> freq = new HashMap<>(); //Big O = 1

            t.play(); // Big O = 1
            tallyChars(reader, freq);
            timeSets[x]= t.check();

            print_tally(freq);


        }
        for(int y = 0;y<50;y++){
            System.out.println((int)timeSets[y]);
        }

    }

    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int b; // will be slower, only check for lowercase chars
        boolean check[] = new boolean[60];
        while ((b = reader.read()) != -1) { // Big O = N
            if((b>96 && b<123)) {
             int c=b-97; // a = 0
                if(check[c]) {
                    freq.put(b, freq.get(b) + 1);
                }
                else {
                    freq.put(b, 1L);
                    check[c]= true;
                }
            }
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
