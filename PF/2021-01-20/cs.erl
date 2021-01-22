-module (cs).
-export ([server/0, start/0, client/2]).

server() ->
   receive
      end_of_list         ->
         exit(normal);
      X when is_number(X) ->
         io:format("~p ~n", [X * X])
   end,
   server().

client(SPID, []) ->
   SPID ! end_of_list;
client(SPID, [Head|Rest]) -> 
   SPID ! Head,
   client(SPID, Rest).

start() -> SPID = spawn(cs, server, []),
           spawn(cs, client, [SPID, [1,2,3,4,5,6]]),
           ok.

