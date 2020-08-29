package uj.jwzp.chat;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Sender implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(Receiver.class);
    private final static String endingMessage = "!bye";

    private final Scanner in;
    private final Gson gson;

    private Connection connection;
    private Channel channel;
    private String queueName;
    private String userName;

    public Sender(InputStream in) {
        this.gson = new Gson();
        this.in = new Scanner(in);
    }

    void initConnection(Connection connection, String queueName, String queueType) throws IOException, TimeoutException {
        this.connection = connection;

        channel = this.connection.createChannel();
        channel.exchangeDeclare(queueName, queueType);
        log.info("Sender established connection to server");

        this.queueName = queueName;
    }

    public String getUserName() {
        System.out.print("\nEnter your username: ");
        userName = in.nextLine();
        return userName;
    }

    @Override
    public void run() {
        String message;

        do {
            System.out.print("[" + userName + "]: ");
            message = in.nextLine();

            if (!message.equals(endingMessage)) {
                Message m = new Message(userName, message);
                try {
                    channel.basicPublish(queueName, "", null, gson.toJson(m).getBytes("UTF-8"));
                } catch (IOException e) {
                    log.warn("Failure during sending message", e);
                }

            }
        } while (!message.equals(endingMessage));
    }
}
