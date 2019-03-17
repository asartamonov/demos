package xmlsoap;

import apitypes.SumRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        try {
            SumRequest request = new SumRequest();
            request.setFirst(1);
            request.setSecond(11);

            SumRequest request2 = new SumRequest();
            request2.setFirst(2);
            request2.setSecond(22);

            SOAPMessage msg = SoapMessageFactory.getSumRequestMessage(request);
            SOAPMessage msg2 = SoapMessageFactory.getSumRequestMessage(request2);

            LOGGER.info("Message 1 ready\n");
            msg.writeTo(System.out); // should send using SoapConnectionFactory write to http connection
            System.out.println("\n");

            LOGGER.info("Message 2 ready\n");
            msg2.writeTo(System.out);
            System.out.println("\n");

            System.out.println("Converted to string");
            String tmpMsg = convertMsgToString(msg2);
            System.out.println(tmpMsg);

            System.out.println("\n");

            System.out.println("Converted back to SOAPMessage");
            convertStringToMsg(tmpMsg).writeTo(System.out);
        } catch (SOAPException | IOException e) {
            LOGGER.error("Error occured");
        }
    }

    private static String convertMsgToString(SOAPMessage msg) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            msg.writeTo(baos);
        } catch (SOAPException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return baos.toString(Charset.forName("UTF-8"));
    }

    private static SOAPMessage convertStringToMsg(String string) {
        ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes(Charset.forName("UTF-8")));
        SOAPMessage message = null;
        try {
            message = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(new MimeHeaders(), bais);
        } catch (IOException | SOAPException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return message;
    }

}
