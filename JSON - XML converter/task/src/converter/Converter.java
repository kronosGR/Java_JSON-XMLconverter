package converter;

import javax.lang.model.element.Name;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Converter {
    Scanner sc = new Scanner(System.in);
    String cont;
    String filename;

    public Converter(String filename) {
        this.filename = filename;
        cont = readFile(this.filename);
    }

    private String readFile(String filename) {
        File f = new File(filename);
        try (FileReader fr = new FileReader(f)) {
            int total;
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024];
            while ((total = fr.read(buf)) != -1) {
                sb.append(new String(buf, 0, total));
            }
            return sb.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public String convert() {
        String converted = "";
        if (isXML()) {
            // convert to JSON
            XMLElement el = XML.parse(cont);
            JSONElement js = XML.toJSON(el);
            System.out.println(js);
        } else {
            // convert to XML
            JSONElement el = JSON.parse(cont);
            XMLElement xml = JSON.toXML(el);
            System.out.println(xml);
        }

        return converted;
    }

    private boolean isXML() {
        if (cont.charAt(0) == '<') {
            return true;
        } else {
            return false;
        }
    }

}
