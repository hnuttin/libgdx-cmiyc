package com.jazzjack.rab.bit.cmiyc.level;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.jazzjack.rab.bit.cmiyc.common.Predictability;

import java.util.stream.Stream;

import static java.lang.String.format;

public class ObjectTypeParser {

    private static final String ELEMENT_OBJECTTYPE = "objecttype";
    private static final String ELEMENT_PROPERTY = "property";

    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_PREDICTABILITY = "predictability";
    private static final String ATTRIBUTE_DEFAULT = "default";

    private final XmlReader.Element objectTypes;

    public ObjectTypeParser(FileHandle fileHandle) {
        XmlReader xmlReader = new XmlReader();
        this.objectTypes = xmlReader.parse(fileHandle);
    }

    public Predictability getEnemyPredictability(String enemyName) {
        XmlReader.Element objectType = getObjectType(enemyName);
        String predictability = getProperty(objectType, ATTRIBUTE_PREDICTABILITY);
        return Predictability.valueOf(predictability);
    }

    private String getProperty(XmlReader.Element objectType, String attribute) {
        XmlReader.Element property = getChildElementWithAttribute(objectType, ELEMENT_PROPERTY, ATTRIBUTE_NAME, attribute);
        return property.getAttribute(ATTRIBUTE_DEFAULT);
    }

    private XmlReader.Element getObjectType(String name) {
        return getChildElementWithAttribute(objectTypes, ELEMENT_OBJECTTYPE, ATTRIBUTE_NAME, name);
    }

    private XmlReader.Element getChildElementWithAttribute(XmlReader.Element parentElement, String childElementName, String attributeName, String attributeValue) {
        Array<XmlReader.Element> objecttypes = parentElement.getChildrenByName(childElementName);
        return Stream.of(objecttypes.items)
                .filter(element -> attributeValue.equalsIgnoreCase(element.getAttribute(attributeName)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format(
                        "No child element '%s' found for attribute name '%s' and attribute value '%s'",
                        childElementName,
                        attributeName,
                        attributeValue)));
    }

}
