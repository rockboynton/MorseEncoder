package boyntonrl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MorseEncoder {

    private static LookupTable<String, String> lookupTable = new LookupTable<>();

    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);
        boolean read = false;
        do {
            try {
                System.out.println("Enter a file to assigning Letters to Morse Code: ");
                File morseFile = new File(stdIn.next());
                loadDecoder(morseFile);
                read = true;
            } catch (FileNotFoundException e) {
                System.err.println("File not Found ");
            } catch (IllegalArgumentException iae) {
                System.err.println("File is formatted incorrectly - " + iae.getLocalizedMessage());
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

    private static void encodeFile(File textFile, File morseFile) {

    }

    private static void loadDecoder(File file) throws FileNotFoundException {
        try (Scanner fileParser = new Scanner(file)) {
            Scanner lineParser;
            while (fileParser.hasNextLine()) {
                lineParser = new Scanner(fileParser.nextLine());
                lookupTable.put(lineParser.next(), lineParser.next());
            }
        }
    }
}
