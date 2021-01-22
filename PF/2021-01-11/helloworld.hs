f :: Int -> Int 
f n = sum . map (^ 2) $ [1..n]


main :: IO()
main = do
            str <- getLine
            print $ f (read str :: Int)