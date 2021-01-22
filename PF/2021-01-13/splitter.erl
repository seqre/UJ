-module (splitter).
-export ([split/1]).

split(List) -> inner_split(List, [], []).

inner_split([], Int, Rest) ->
    {Int, Rest};

% inner_split([Head|Tail], Int, Rest) when is_integer(Head) ->
%    inner_split(Tail, Int ++ [Head], Rest);
% inner_split([Head|Tail], Int, Rest) ->
%    inner_split(Tail, Int, Rest ++ [Head]).

inner_split([Head|Tail], Int, Rest) ->
    if 
        is_integer(Head) ->
            inner_split(Tail, Int ++ [Head], Rest);

        true ->
            inner_split(Tail, Int, Rest ++ [Head])
    end.