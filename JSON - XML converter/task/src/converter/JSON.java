package converter;

import java.util.ArrayList;
import java.util.List;

public class JSON {

    private static String NAME_REG = "\"[^\"]+\"";
    private static String VALUE_REG = "null|\\d+|\"[^\"]*\"";
    private static String SPLITTER_REG = "\\s*:\\s*";
    private static String JSON_REG = "^\\{\\s*" + NAME_REG + SPLITTER_REG +
            "(" + VALUE_REG + "" +
            "|\\{\\s*" + NAME_REG + SPLITTER_REG + "(" + VALUE_REG + ")" +
            "(\\s*,\\s*" + NAME_REG + SPLITTER_REG + "(" + VALUE_REG + "))*\\s*})" +
            "(\\s*,\\s*" + NAME_REG + SPLITTER_REG +
            "(" + VALUE_REG + "" +
            "|\\{\\s*" + NAME_REG + SPLITTER_REG + "(" + VALUE_REG + ")" +
            "(\\s*,\\s*" + NAME_REG + SPLITTER_REG + "(" + VALUE_REG + "))*\\s*}))*" +
            "\\s*}$";

    public static JSONElement parse(String input) {
        boolean isValid = input.matches(JSON_REG);
        if (!isValid) {
            throw new IllegalArgumentException("No valid input: " + input);
        }

        int splitIndex = input.indexOf(":");
        String name = input.substring(0, splitIndex);
        String elName = getElementName(input);
        String value = input.substring(splitIndex + 1).trim();

        if (value.startsWith("null")) return new JSONElement(elName);
        else if (name.startsWith("\"")) {
            int endIndex = name.indexOf("\"", 1);
            String elValue = value.substring(1, endIndex);
            return new JSONElement(elName, elValue);
        } else if (name.startsWith("{")) {
            String[] blocks = value.replace("{", "").split("\s*,\s*");
            List<JSONEntity> entities = new ArrayList<>();
            for (String block : blocks) {
                entities.add(getEntities(block));
            }
            return new JSONElement(elName, new JSONElement(entities));
        } else {
            throw new IllegalArgumentException("No valid value " + value);
        }
    }

    public static XMLElement toXML(JSONElement element){
        List<JSONEntity> entities = element.getEntities();
        JSONEntity entity = entities.get(0);
        if (entity.getValue() == null){
            return new XMLElement(entity.getName());
        }
        else if (!(entity.getValue()  instanceof JSONElementValue)) {
            final JSONValue jsonValue = (JSONValue) entity.getValue();
            return new XMLElement(entity.getName(), jsonValue.toString());
        } else {
            final JSONElementValue attributeValue = (JSONElementValue) entity.getValue();
            final List<XMLAttribute> attributes = new ArrayList<>();
            String elementValue = null;
            for (JSONEntity valueEntity : attributeValue.getValue().getEntities()) {
                final String entityName = valueEntity.getName();
                final JSONValue entityValue = (JSONValue) valueEntity.getValue();
                if (entityName.startsWith("@")) {
                    attributes.add(new XMLAttribute(entityName.substring(1), entityValue.toString().replace("\"","")));
                } else if (entityName.startsWith("#")) {
                    elementValue = entityValue.toString().replace("\"","");
                }
            }
            return new XMLElement(entity.getName(), elementValue, attributes);
        }
    }

    private static String getElementName(String rawName) {
        int beginIndex = rawName.indexOf("\"");
        int endIndex = rawName.indexOf("\"", beginIndex + 1);
        return rawName.substring(beginIndex + 1, endIndex);
    }

    private static JSONEntity getEntities(String raw) {
        int splitIndex = raw.indexOf(":");
        String name = raw.substring(0, splitIndex);
        String elName = getElementName(name);
        String value = raw.substring(splitIndex + 1).trim();
        if (value.startsWith("null")) {
            return new JSONEntity(elName);
        } else if (value.startsWith("\"")) {
            final int valueEndIndex = value.indexOf("\"", 1);
            final String elementValue = value.substring(1, valueEndIndex);
            return new JSONEntity(elName, elementValue);
        } else {
            return new JSONEntity(elName, value);
        }
    }
}
