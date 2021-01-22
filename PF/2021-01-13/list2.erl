-module (list2).
-export ([srednia/1]).

srednia([])   -> 0;  
srednia(List) -> inner_srednia(List, 0, 0).
%srednia(List) -> sum(List) / count (List).

inner_srednia([], Sum, Count) ->
    Sum / Count;
inner_srednia([Head|Tail], Sum, Count) ->
    inner_srednia(Tail, Sum + Head, Count + 1).

sum([])          -> 0;
sum([Head|Tail]) -> Head + sum(Tail).

count([])       -> 0;
count([_|Tail]) -> 1 + count(Tail).