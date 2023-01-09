package converter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XML {
    private static final String XML_REG = "^<[a-zA-Z][\\w_-]*(\\s+[a-zA-Z][\\w_-]*(\\s*=\\s*\"\\s*[\\w'-_]*\\s*\")?)*(>[\\w'-_ ]*</[a-zA-Z][\\w_-]*|\\s*/)>$";
    private static final Pattern ELEM_REG = Pattern.compile("[a-zA-Z][\\w_-]*");
    private static final Pattern ATTR_REG = Pattern.compile("\\s+[a-zA-Z][\\w_-]*(\\s*=\\s*\"\\s*[\\w'-_]*\\s*\")?");
    private static final Pattern VAL_REG = Pattern.compile(">[\\w'-_][\\w'-_ ]*</");

    public static XMLElement parse(String input){
        boolean isValid = input.matches(XML_REG);
        if (!isValid) throw new IllegalArgumentException("Not xml");

        Matcher matcher = ELEM_REG.matcher(input);
        if (!matcher.find()) throw new IllegalArgumentException("Not valid element");

        String elName = matcher.group();
        ArrayList<XMLAttribute> elAttrs = new ArrayList<>();
        matcher = ATTR_REG.matcher(input.substring(0, input.indexOf(">")));

        while (matcher.find()){
            String attr = matcher.group();
            if (!attr.contains("=")){
                elAttrs.add(new XMLAttribute(attr.trim()));
            } else {
                String[] blocks = attr.split("\\s*=\\s*");
                String attrName = blocks[0].trim();
                String attrValue = blocks[1];
                int beginIndex = attrValue.indexOf("\"");
                int endIndex = attrValue.indexOf("\"", beginIndex + 1);
                String attributeValue = attrValue.substring(beginIndex + 1, endIndex);
                if (attributeValue.isBlank()) {
                    elAttrs.add(new XMLAttribute(attrName));
                } else {
                    elAttrs.add(new XMLAttribute(attrName, attributeValue));
                }
            }
        }
        matcher = VAL_REG.matcher(input);
        if (!matcher.find()) {
            if (elAttrs.isEmpty()) {
                return new XMLElement(elName);
            } else {
                return new XMLElement(elName, elAttrs);
            }
        }
        final String value = matcher.group();
        final String elementValue = value.substring(1, value.length() - 2);
        return new XMLElement(elName, elementValue, elAttrs);
    }

    public static  JSONElement toJSON(XMLElement element){
        String elName = element.getName();
        String elValue = element.getValue();
        List<XMLAttribute> attributes = element.getAttrs();
        if (attributes == null){
            return new JSONElement(elName, elValue);
        } else {
            List<JSONEntity> entities = new ArrayList<>(attributes.size()+1);
            for (XMLAttribute attribute: attributes){
                entities.add(new JSONEntity("@" + attribute.getName(), attribute.getValue()));
            }
            entities.add(new JSONEntity("#" + elName, elValue));
            return  new JSONElement(elName, new JSONElement(entities));
        }
    }
}
