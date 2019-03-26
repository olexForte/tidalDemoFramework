package entities;

import utils.Diff;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.Field;

public abstract class BaseEntity {

    protected static <T> Unmarshaller getUnmarshaller(Class<T> type) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(type);
        return jc.createUnmarshaller();
    }

    protected static <T> Marshaller getMarshaller(Class<T> type) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(type);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    protected static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        return docBuilderFactory.newDocumentBuilder();
    }

    public static <T> Diff CompareEntities(T expected, T actual) throws IllegalAccessException {
        Field[] fields = expected.getClass().getDeclaredFields();

        Diff diff = new Diff();

        for(Field field: fields){
            Object expectedValue = field.get(expected);
            Object actualValue = field.get(actual);

            if(expectedValue != null && !expectedValue.equals(actualValue))
                diff.Add(field, expectedValue, actualValue);
        }

        return diff;
    }
}
