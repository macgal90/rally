package rally;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SaveLoad {
    public static Integer odczytajPlik(String nazwaPliku) {

        File plikDane = new File(nazwaPliku);
        int odczyt = 0;
        try {
            Scanner skaner = new Scanner(plikDane);
            odczyt = skaner.nextInt();
        } catch (FileNotFoundException e) {
            System.out.println("Brak Pliku do odczytania!");
        }
        return odczyt;
    }

    public static String odczytajPlikString(String nazwaPliku) {

        File plikDane = new File(nazwaPliku);
        String odczyt = "";
        try {
            Scanner skaner = new Scanner(plikDane);
            odczyt = skaner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Brak Pliku do odczytania!");
        }
        return odczyt;
    }

    public static void zapiszPlik(String nazwaPliku, int wynik) {
        try {
            PrintWriter out = new PrintWriter(nazwaPliku);
            out.print(wynik);
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Niestety, nie mogę utworzyć pliku!");
        }
    }

    public static void zapiszPlikString(String nazwaPliku, String wynik) {
        try {
            PrintWriter out = new PrintWriter(nazwaPliku);
            out.print(wynik);
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Niestety, nie mogę utworzyć pliku!");
        }
    }
}
