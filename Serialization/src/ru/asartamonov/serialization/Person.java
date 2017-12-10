package ru.asartamonov.serialization;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/*
 * @XmlRootElement
 * Customizable name for XML root element, default - classname, can be any, for example @XmlRootElement(name="human")
 * will result <human></human> instead of <person></person>
 *
 * @XmlType
 * Order win which fields will appear in XML, all fields must be listed here, even transient or else -
 * Property ___someName___ is present but not specified in @XmlType.propOrder
 * fields marked @XmlAttribute may or may not listed in propOrder
 * fields annotated as @XmlTransient should not be listed
 *
 * @XmlAttribute
 * define the id field is mapped as an attribute instead of an element
 *
 * @XmlTransient
 * property with this annotation on it's getter will not appear in serialized XML (even its name!)
 *
 * @XmlElement
 * override default name of XML element, for example @XmlElement(name = "secretString") on getter
 *
 * @XmlJavaTypeAdapter
 * Using JAXB’s XmlAdapter, we may define a custom code to convert an 'unmappable' class into something that JAXB can handle.
 * public class DateAdapter extends XmlAdapter<TypeOne, TypeTwo> {}
 * @Override
 * 1 - public TypeTwo unmarshal(TypeOne v) throws Exception {...}
 * 2 - public TypeOne marshal(TypeTwo v) throws Exception {...}
 *
 * List<T> mapping - annotate getter method as
 * @XmlElement(name = "friend", type = Person.class) // how 1 list element should be named in resulting XML
 * @XmlElementWrapper(name = "friends") // how list wrapper element should be named in resulting XML
 *
 * @XmlAccessorOrder Controls the ordering of fields and properties in a class. Useful if you don't want to list every property https://docs.oracle.com/cd/E19316-01/819-3669/bnbcz/index.html
 * @XmlAccessorType (example @XmlAccessorType(XmlAccessType.FIELD)) if we are using field access, the element names will be based on the field names.
 *
 * Possible questions: will it be serialized?
 * static fields - NO
 * private fields without getters/setters - NO
 *
*/
@XmlRootElement(name = "human")
@XmlType(propOrder = {"transString", "name", "friends", "attributeField"}) // prevails over the @XmlAccessorOrder
public class Person implements Serializable {
    private List<Person> friends;
    private String name;
    private String attributeField;
    private transient String transString; // property with transient modifier serialized, but not its value, 'null' after deserialization
    private String xmlTransString;
    private String privateStringWithoutGetter;
    private static String privateStaticStringWithoutGetter; // JAXB is something that ignores static fields by design

    public static String getPrivateStaticStringWithoutGetter() {
        return privateStaticStringWithoutGetter;
    }

    public static void setPrivateStaticStringWithoutGetter(String privateStaticStringWithoutGetter) {
        Person.privateStaticStringWithoutGetter = privateStaticStringWithoutGetter;
    }

    public Person() { // obligatory, else - error "does not have a no-arg default constructor"
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, List<Person> friends) {
        this.name = name;
        this.friends = friends;
        this.privateStringWithoutGetter = "we will lost during XML serialization, but not in writeObject";
    }

    public String getAttributeField() {
        return attributeField;
    }

    @XmlAttribute
    public void setAttributeField(String attributeField) {
        this.attributeField = attributeField;
    }

    @XmlTransient
    public String getXmlTransString() {
        return xmlTransString;
    }

    public void setXmlTransString(String xmlTransString) {
        this.xmlTransString = xmlTransString;
    }

    @XmlElement(name = "atransientString")
    public String getTransString() {
        return transString;
    }

    public void setTransString(String transString) {
        this.transString = transString;
    }

    @Override
    public String toString() {
        return "Person{" +
                "friends=" + friends +
                ", name='" + name + '\'' +
                ", attributeField='" + attributeField + '\'' +
                ", transString='" + transString + '\'' +
                ", xmlTransString='" + xmlTransString + '\'' +
                ", privateStringWithoutGetter='" + privateStringWithoutGetter + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "friend", type = Person.class)
    @XmlElementWrapper(name = "friends")
    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }
}
