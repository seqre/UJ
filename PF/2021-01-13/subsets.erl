-module (subsets).
-export ([subsets/2]).

subsets(_, 0) -> [[]];
subsets([], _) -> [];
subsets([Head|Tail], K) -> 
    lists:map(fun(X) -> [Head] ++ X end, subsets(Tail, K - 1)) ++ subsets(Tail, K).



