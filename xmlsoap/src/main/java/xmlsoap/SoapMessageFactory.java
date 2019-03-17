package xmlsoap;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apitypes.SumRequest;

/**
 * Thread safe message builder for SOAP messages.
 * <p>
 * 1. Provide base message (input args: soapaction).
 * <p>
 * 2. Add body serialized from Java (JAXB annotated classes)
 * <p>
 * 3. Add headers based on soapaction Overall args set: soapaction, request
 * object, header params.
 * 
 * @author asartamonov
 *
 */
public class SoapMessageFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapMessageFactory.class);
    private static final Map<Class<?>, JAXBContext> class2contexts = new HashMap<>();

    private static SOAPMessage getBase() throws SOAPException {
        LOGGER.info("Building base message");
        javax.xml.soap.MessageFactory messageFactory = javax.xml.soap.MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPHeader header = soapMessage.getSOAPHeader();
        header.setAttribute("hdr_attr", UUID.randomUUID().toString());
        soapMessage.saveChanges();
        return soapMessage;
    }

    public static SOAPMessage getSumRequestMessage(SumRequest request) throws SOAPException {
        // getBase, serialize request, return message

        LOGGER.info("Building message for request {}", request);

        SOAPMessage message = getBase();
        JAXBContext jaxbContext = class2contexts.get(request.getClass());
        try {
            if (jaxbContext == null) {
                LOGGER.info("JAXBcontext added to cache for class {}",request.getClass());
                jaxbContext = JAXBContext.newInstance(SumRequest.class);
                class2contexts.put(request.getClass(), jaxbContext);
            } else {
                LOGGER.info("JAXBcontext taken from cache for class {}",request.getClass());
            }
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(request, message.getSOAPBody());
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
        }
        message.saveChanges();
        return message;
    }
}
