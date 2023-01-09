package converter;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class JSONElement {
    private List<JSONEntity> entities;

    public JSONElement(List<JSONEntity> entities){
        this.entities = entities;
    }

    public JSONElement(String name, String value){
        this(Collections.singletonList(new JSONEntity(name, value)));
    }

    public JSONElement(String name, JSONElement value){
        this(Collections.singletonList(new JSONEntity(name, value)));
    }

    public JSONElement(String name){
        this(Collections.singletonList(new JSONEntity(name)));
    }

    public List<JSONEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<JSONEntity> entities) {
        this.entities = entities;
    }

    public String toString(){
        StringJoiner sj = new StringJoiner(",");
        for(JSONEntity en : entities){
            sj.add(en.toString());
        }
        return String.format("{%s}", sj.toString());
    }
}
