package converter;

public class XMLAttribute {

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

    public XMLAttribute(String name, String value){
        this.name = name;
        this.value = value;
    }

    public XMLAttribute(String name){
        this(name, null);
    }

    public String toString(){
        if (value == null){
            return name;
        } else {
            return String.format("%s=\"%s\"", name, value);
        }
    }
}
