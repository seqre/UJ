-module (poczatek).
-export ([start/0, receiver/0, sender/1]).

receiver() -> io:format("Receiver started with PID=~p ~n", [self()]),
              receive
                X -> io:format("Receiver received atom ~p ~n", [X])
              end.

sender(RPID) -> io:format("Sender started with PID=~p ~n", [self()]),
                io:format("Sender sends atom ~p to receiver", [hello]),   
                RPID ! hello.

start() -> RPID = spawn(poczatek, receiver, []),
           spawn(poczatek, sender, [RPID]).