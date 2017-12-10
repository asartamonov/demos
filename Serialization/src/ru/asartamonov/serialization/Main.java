package ru.asartamonov.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {
    private static final String serPath = "/home/asartamonov/Documents/java/demos/Serialization/serialPerson.xml";
    private static final String wrObjPath = "/home/asartamonov/Documents/java/demos/Serialization/swrObjPerson.j";
//    private static final String serPath = "C:\\Users\\asartamonov\\Documents\\excercises-thinking\\Serialization\\serialPerson.xml";
//    private static final String wrObjPath = "C:\\Users\\asartamonov\\Documents\\excercises-thinking\\Serialization\\swrObjPerson.j";

    public static void main(String[] args) {

        File outFile = new File(serPath);
        File outFile2 = new File(wrObjPath);
        File inFile = new File(serPath);

        // JAXB is smart enough to handle special symbols
        Person serializedPerson = new Person("Dave<!--", Arrays.asList(new Person("Stewart"), new Person("Charlie")));
        serializedPerson.setTransString("transient are serialized in XML, but not in oos writeObject");
        serializedPerson.setAttributeField("tallMan");
        serializedPerson.setXmlTransString("@XMLTransient string are not serialized in XML, but serialized in oos writeObject");

        /* JAXB serialization */

        /* write */
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // pretty prints resulting XML

            jaxbMarshaller.marshal(serializedPerson, outFile);
            jaxbMarshaller.marshal(serializedPerson, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        /* read */
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Person deSerializedPerson = (Person) unmarshaller.unmarshal(inFile);

            System.out.println("Unmarshalled from XML: " + deSerializedPerson);
            System.out.println("Same class in JAXB serialization? " + (deSerializedPerson.getClass() == new Person().getClass()));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        /* Built-in serialization */
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outFile2));
            oos.writeObject(serializedPerson);

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outFile2));
            Person p = (Person) ois.readObject();

            System.out.println("Read from byte[]: " + p);
            System.out.println("Same class in writeObject serialization? " + (p.getClass() == new Person().getClass()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

/*
* Output:
*
* <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<human attributeField="tallMan">
    <atransientString>transient are serialized in XML, but not in oos writeObject</atransientString>
    <name>Dave&lt;!--</name>
    <friends>
        <friend>
            <name>Stewart</name>
        </friend>
        <friend>
            <name>Charlie</name>
        </friend>
    </friends>
</human>
Unmarshalled from XML: Person{friends=[Person{friends=null, name='Stewart', attributeField='null', transString='null', xmlTransString='null', privateStringWithoutGetter='null'}, Person{friends=null, name='Charlie', attributeField='null', transString='null', xmlTransString='null', privateStringWithoutGetter='null'}], name='Dave<!--', attributeField='tallMan', transString='transient are serialized in XML, but not in oos writeObject', xmlTransString='null', privateStringWithoutGetter='null'}
Same class in JAXB serialization? true
Read from byte[]: Person{friends=[Person{friends=null, name='Stewart', attributeField='null', transString='null', xmlTransString='null', privateStringWithoutGetter='null'}, Person{friends=null, name='Charlie', attributeField='null', transString='null', xmlTransString='null', privateStringWithoutGetter='null'}], name='Dave<!--', attributeField='tallMan', transString='null', xmlTransString='@XMLTransient string are not serialized in XML, but serialized in oos writeObject', privateStringWithoutGetter='we will lost during XML serialization, but not in writeObject'}
Same class in writeObject serialization? true
* */
