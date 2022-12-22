package converter;

import javax.lang.model.element.Name;
import java.util.Scanner;

public class Converter {
    Scanner sc = new Scanner(System.in);
    String cont;

    public void start() {
        cont = sc.nextLine();
    }

    public String convert() {
        String converted = "";
        if (isXML()) {
            // convert to JSON
            converted = toJSON();
        } else {
            // convert to XML
            converted = toXML();
        }

        return converted;
    }

    private String toJSON() {
        String name = "";
        String value = "";
        if (cont.matches("<.+/>")) {
            name += cont.substring(1, cont.lastIndexOf('/'));
            return String.format("{\"%s\":null}", name);
        } else {
            name = cont.substring(1, cont.indexOf(">"));
            value += cont.substring(cont.indexOf('>') + 1, cont.lastIndexOf("<"));
            return String.format("{\"%s\": \"%s\"}", name, value);
        }
    }

    private String toXML() {
        String arr[] = cont.split(":");
        String name = arr[0].replace("\"", "").replace("{", "").trim();
        String value = arr[1].replace("\"", "").replace("}", "").trim();
        if (arr[1].contains("null")) {
            return String.format("<%s/>", name);
        } else {
            return String.format("<%s>%s</%s>", name, value, name);
        }
    }

    private boolean isXML() {
        if (cont.charAt(0) == '<') {
            return true;
        } else {
            return false;
        }
    }
}
