/*
 * CS2852
 * Spring 2018
 * Lab 8 - Morse Code Encoder
 * Name: Rock Boynton
 * Created: 5/7/2018
 */

package boyntonrl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Reads a text file and writes the Morse encoded result to a text file.
 */
public class MorseEncoder {

    private static LookupTable<String, String> lookupTable;

    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);
        boolean read = false;
        do {
            try {
                lookupTable = new LookupTable<>();
                System.out.println("Enter a file to assigning Letters to Morse Code: ");
                File morseFile = new File(stdIn.next());
                loadDecoder(morseFile);
                read = true;
            } catch (FileNotFoundException e) {
                System.err.println("File not Found ");
            } catch (IllegalArgumentException iae) {
                System.err.println("File is formatted incorrectly - " + iae.getLocalizedMessage());
            } catch (NoSuchElementException nsea) {
                System.err.println("File is formatted incorrectly");
            }
        } while (!read);

        read = false;
        do {
            try {
                System.out.println("Enter an input filename: ");
                File textFile = new File(stdIn.next());
                System.out.println("Enter an output filename: ");
                File morseFile = new File(stdIn.next());
                encodeFile(morseFile, textFile);
                read = true;
                System.out.println(morseFile.toString() + " successfully decoded!");
            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            }
        } while (!read);
    }

    private static void encodeFile(File textFile, File morseFile) throws FileNotFoundException {
        try (Scanner parser = new Scanner(morseFile)) {
            try (PrintWriter writer = new PrintWriter(textFile)){
                String line;
                String[] words;
                while (parser.hasNextLine()) {
                    line = parser.nextLine().toUpperCase();
                    words = line.split(" ");
                    String[] characters;
                    for (String word : words) {
                        if (!word.trim().isEmpty()) {
                            characters = word.split("");
                            for (String character : characters) {
                                String code = lookupTable.get(character);
                                if (code != null) {
                                    writer.print(code);
                                    writer.print(" ");
                                } else {
                                    System.err.println("Warning: skipping " + character);
                                }
                            }
                            writer.print(" | ");
                        }
                    }
                    writer.println();
                }
            }
        }
    }

    private static void loadDecoder(File file) throws FileNotFoundException,
            NoSuchElementException {
        try (Scanner fileParser = new Scanner(file)) {
            Scanner lineParser;
            while (fileParser.hasNextLine()) {
                lineParser = new Scanner(fileParser.nextLine());
                lookupTable.put(lineParser.next(), lineParser.next());
            }
        }
    }
}
