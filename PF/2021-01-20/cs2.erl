-module (cs2).
-export ([server/0, start/0, client/2]).

server() ->
   receive
      {CPID, X} when is_number(X) ->
         CPID ! (X * X),
         server()

      after 1000          ->
         io:write("Server is going down...~n")
   end.

client(_, []) ->
   done;
client(SPID, [Head|Rest]) -> 
   SPID ! {self(), Head},
   receive
      X ->
         io:format("~p ~n", [X]),
         client(SPID, Rest)
   end.

start() -> SPID = spawn(cs, server, []),
           spawn(cs, client, [SPID, [1,2,3,4,5,6]]),
           ok.

