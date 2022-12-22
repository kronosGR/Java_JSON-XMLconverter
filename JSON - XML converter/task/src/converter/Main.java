package converter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Converter conv = new Converter();
        conv.start();
        System.out.println(conv.convert());
    }
}
