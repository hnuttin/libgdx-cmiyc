package com.jazzjack.rab.bit.cmiyc.level.meta;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class ObjectTypeParser {

    private static final String ELEMENT_OBJECTTYPE = "objecttype";
    private static final String ELEMENT_PROPERTY = "property";

    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_DEFAULT = "default";

    private final XmlReader.Element objectTypes;

    public ObjectTypeParser(FileHandle fileHandle) {
        XmlReader xmlReader = new XmlReader();
        this.objectTypes = xmlReader.parse(fileHandle);
    }

    public Map<String, String> getDefaultProperties(String objectType) {
        Map<String, String> defaultProperties = new HashMap<>();
        XmlReader.Element objectTypeElement = getObjectType(objectType);
        if (objectTypeElement != null) {
            Array<XmlReader.Element> objectTypeProperties = objectTypeElement.getChildrenByName(ELEMENT_PROPERTY);
            for (XmlReader.Element propertyElement : objectTypeProperties) {
                defaultProperties.put(propertyElement.getAttribute(ATTRIBUTE_NAME), propertyElement.getAttribute(ATTRIBUTE_DEFAULT));
            }
        }
        return defaultProperties;
    }

    private XmlReader.Element getObjectType(String name) {
        return getChildElementWithAttribute(objectTypes, ELEMENT_OBJECTTYPE, ATTRIBUTE_NAME, name);
    }

    private XmlReader.Element getChildElementWithAttribute(XmlReader.Element parentElement, String childElementName, String attributeName, String attributeValue) {
        Array<XmlReader.Element> objecttypes = parentElement.getChildrenByName(childElementName);
        return Stream.of(objecttypes.items)
                .filter(Objects::nonNull)
                .filter(element -> attributeValue.equalsIgnoreCase(element.getAttribute(attributeName)))
                .findFirst()
                .orElse(null);
    }

}
