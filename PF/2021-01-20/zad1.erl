-module (zad1).
-export ([start/1, proces/1]).

proces(1) -> io:format("Tu proces: ~p ~n", [self()]);
proces(N) -> proces(1),
             proces(N - 1).

start(1) -> spawn(zad1, proces, [3]);
start(N) -> start(1),
            start(N - 1).