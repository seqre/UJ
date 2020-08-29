package uj.jwzp.chat;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.TimeoutException;

public class Receiver implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(Receiver.class);

    private final Gson gson;
    private final PrintStream out;

    private DeliverCallback deliverCallback;
    private Connection connection;
    private String queueName;
    private Channel channel;
    private String userName;

    public Receiver(PrintStream out) {
        this.gson = new Gson();
        this.out = out;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    void initConnection(Connection connection, String queueName, String queueType) throws IOException, TimeoutException {
        this.connection = connection;

        channel = this.connection.createChannel();
        channel.exchangeDeclare(queueName, queueType);
        this.queueName = channel.queueDeclare().getQueue();
        channel.queueBind(this.queueName, queueName, "");
        log.info("Receiver established connection to server");

        deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Message m = gson.fromJson(message, Message.class);
            if (!m.getUser().equals(userName)) {
                out.println("[" + m.getUser() + "]: " + m.getMessage());
            }
        };
    }

    @Override
    public void run() {
        log.info("Waiting for messages");
        try {
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            log.error("Error during waiting for messages", e);
        }
    }
}