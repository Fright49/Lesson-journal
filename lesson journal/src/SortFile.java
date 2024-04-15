import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SortFile {
    public static void scan(String[] args) throws IOException {
        // Otwórz plik i odczytaj jego zawartość do listy
        File file = new File("test");
        Scanner scanner = new Scanner(file);
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        scanner.close();

        // Posortuj zawartość pliku
        Collections.sort(lines);

        // Zapisz posortowaną zawartość do pliku
        FileWriter myWriter = new FileWriter("test", false);
        for (String line : lines) {
            myWriter.write(line + "\n");
        }
        myWriter.close();
    }
}
