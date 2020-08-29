Task: create an interactive command-line chat client, that will be listening for messages with user and content (see format below), and will be displaying them.
Also, it should be able to send a chat message, that others can read it.
Program should also be closeable by user (with some command, or CTRL+C shortcut).
For messages exchange, Rabbit MQ should be used (see server details below).

JSON format of message: `{"user":"Arek","message":"Hello World!"}`.


Server is Rabbit MQ, free tier from www.cloudamqp.com.
* HOST: `bear.rmq.cloudamqp.com`
* USER: `fwknozud`
* VIRTUAL HOST: `fwknozud`
* QUEUE: `chat`
* PASSWORD: `omy9Kbzj6Q56GOqb27_iP8MXAK1CVDoL`
* EXCHANGE NAME: `chat`
* EXCHANGE TYPE: `fanout`

