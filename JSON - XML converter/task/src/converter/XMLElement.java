package converter;

import java.util.List;
import java.util.StringJoiner;

public class XMLElement {
    private List<XMLAttribute> attrs;
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<XMLAttribute> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<XMLAttribute> attrs) {
        this.attrs = attrs;
    }

    public XMLElement(String name, String value, List<XMLAttribute> attrs) {

        this.name = name;
        this.value = value;
        this.attrs = attrs;
    }

    public XMLElement(String name, List<XMLAttribute> attrs) {
        this(name, null, attrs);
    }

    public XMLElement(String name, String value) {
        this(name, value, null);
    }

    public XMLElement(String name) {
        this(name, null, null);
    }

    public String toString() {
        if (value == null && attrs == null) {
            return String.format("<%s/>", name);
        } else if (value == "null") {
            final StringJoiner attributeJoiner = new StringJoiner(" ");
            for (XMLAttribute attribute : attrs) {
                attributeJoiner.add(attribute.toString());
            }
            return String.format("<%s %s/>", name, attributeJoiner.toString());
        } else if (attrs == null) {
            return String.format("<%s>%s</%s>", name, value, name);
        } else {
            final StringJoiner aj = new StringJoiner(" ");
            for (XMLAttribute attribute : attrs) {
                aj.add(attribute.toString());
            }
            return String.format("<%s %s>%s</%s>", name, aj.toString(), value, name);
        }
    }
}
