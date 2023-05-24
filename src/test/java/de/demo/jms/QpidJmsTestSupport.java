package de.demo.jms;

import java.util.UUID;

import org.apache.qpid.jms.JmsConnectionFactory;

import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;

public class QpidJmsTestSupport {

    /**
     *As was used in {@link QpidJmsEndpoint}
     */
    public static final String RECEIVE_MESSAGE_ENDPOINT_PATH = "/messages/last";
    public static final String SEND_MESSAGE_ENDPOINT_PATH = "/messages/send";
    
    public static final String RECEIVE_PERSONS_ENDPOINT_PATH = "/persons/last";
    public static final String SEND_PERSONS_ENDPOINT_PATH = "/persons/send";
    

    /**
     * As matches acceptor defined in src/test/resources/broker.xml,
     * and used in src/main/resources/application.properties
     */
//    public static final String CONNECTION_URL = "amqp://localhost:5672";
    
    public static final String CONNECTION_URL = "amqp://localhost:15672";

    public static JMSContext createContext() throws JMSException  {
        JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory("quarkus","quarkus",CONNECTION_URL);

//        * @see JMSContext#SESSION_TRANSACTED
//        * @see JMSContext#CLIENT_ACKNOWLEDGE
//        * @see JMSContext#AUTO_ACKNOWLEDGE
//        * @see JMSContext#DUPS_OK_ACKNOWLEDGE
        
        return jmsConnectionFactory.createContext(JMSContext.DUPS_OK_ACKNOWLEDGE);
    }

    public static String generateBody() {
        return UUID.randomUUID().toString();
    }

}
