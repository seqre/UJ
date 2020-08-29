package uj.jwzp.chat;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Client {
    private final static Logger log = LoggerFactory.getLogger(Receiver.class);

    private final static String HOST = "bear.rmq.cloudamqp.com";
    private final static String USER = "fwknozud";
    private final static String VIRTUAL_HOST = "fwknozud";
    private final static String QUEUE = "chat";
    private final static String PASSWORD = "omy9Kbzj6Q56GOqb27_iP8MXAK1CVDoL";
    private final static String EXCHANGE_NAME = "chat";
    private final static String EXCHANGE_TYPE = "fanout";

    private static ConnectionFactory factory;
    private static Connection connection;

    public static void main(String[] args) throws InterruptedException {
        Receiver receiver = new Receiver(System.out);
        Sender sender = new Sender(System.in);

        try {
            setup();
        } catch (IOException | TimeoutException e) {
            log.error("Failure during connection setup.");
        }

        try {
            receiver.initConnection(connection, QUEUE, EXCHANGE_TYPE);
        } catch (IOException | TimeoutException e) {
            log.error("Failure during receiver setup.");
        }

        try {
            sender.initConnection(connection, QUEUE, EXCHANGE_TYPE);
        } catch (IOException | TimeoutException e) {
            log.error("Failure during sender setup.");
        }

        receiver.setUserName(sender.getUserName());

        Thread receiverThread = new Thread(receiver);
        Thread senderThread = new Thread(sender);

        receiverThread.start();
        senderThread.start();

        try {
            senderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }

    private static void setup() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(USER);

        connection = factory.newConnection();
    }
}
