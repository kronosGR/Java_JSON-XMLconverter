package converter;

public class JSONValue {
    private String value;

    public JSONValue(String value){
        this.value = value;
    }

    public JSONValue(){
        this(null);
    }

    public String toString(){
        if (value != null){
            return String.format("\"%s\"", value);
        } else {
            return "null";
        }
    }

}
