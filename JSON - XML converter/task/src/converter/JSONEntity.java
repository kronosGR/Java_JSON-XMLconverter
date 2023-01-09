package converter;

public class JSONEntity {
    private String name;
    private JSONValue value;

    public JSONEntity(String name, JSONValue value) {
        this.name = name;
        this.value = value;
    }

    public JSONEntity(String name) {
        this(name, new JSONValue());
    }

    public JSONEntity(String name, String value) {
        this(name, new JSONValue(value));
    }

    public JSONEntity(String name, JSONElement value) {
        this(name, new JSONElementValue(value));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONValue getValue() {
        return value;
    }

    public void setValue(JSONValue value) {
        this.value = value;
    }

    public String toString() {
        return String.format("\"%s\":%s", name, value.toString());
    }
}
