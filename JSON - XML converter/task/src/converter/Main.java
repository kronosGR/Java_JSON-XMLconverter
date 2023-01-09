package converter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Converter conv = new Converter("test.txt");
        System.out.println(conv.convert());
    }
}
