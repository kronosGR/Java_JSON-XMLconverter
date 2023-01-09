package converter;

public class JSONElementValue extends JSONValue {
    private JSONElement value;

    public JSONElementValue(JSONElement value){
        this.value = value;
    }

    public String toString(){
        return value.toString();
    }

    public JSONElement getValue() {
        return value;
    }

    public void setValue(JSONElement value) {
        this.value = value;
    }
}
