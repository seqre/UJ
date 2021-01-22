-module (prog1).
-export ([binom/2]).

% Komentarz: funkcja obliczajaca silnie

silnia (0) -> 1;
silnia (N) -> N * silnia (N-1).

binom(N,K) -> silnia(N) / (silnia(K) * silnia (N-K)).

