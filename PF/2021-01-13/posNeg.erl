-module (posNeg).
-export ([posNeg/1]).

posNeg(List) -> inner_posNeg2(List, 0, 0).

inner_posNeg2([], Pos, Neg)->
    {Pos, Neg};
inner_posNeg2([Head|Tail], Pos, Neg) when Head > 0 ->
    inner_posNeg2(Tail, Pos + 1, Neg);
inner_posNeg2([_|Tail], Pos, Neg) ->
    inner_posNeg2(Tail, Pos, Neg + 1).