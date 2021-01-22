squaresList :: Int -> [Int]
squaresList n = [x^2 | x <- [1..n]]

displayList :: [Int] -> IO()
displayList []     = return()
displayList (n:ns) = do
                        print n
                        displayList ns

main :: IO()
main = do
            str <- getLine 
            displayList . squaresList $ read str