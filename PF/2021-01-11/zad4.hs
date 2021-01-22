import System.IO
import System.Environment ( getArgs )

writeListToFile :: Handle -> [String] -> IO ()
writeListToFile _ []          = return()
writeListToFile handle (s:ss) = do
                                    hPutStrLn handle s
                                    writeListToFile handle ss

genPalin :: Int -> [String]
genPalin 0 = [""]
genPalin 1 = ["a", "b"]
genPalin n = map (\x -> "a" ++ x ++ "a") (genPalin (n - 2)) ++ map (\x -> "b" ++ x ++ "b") (genPalin (n - 2))

main :: IO ()        
main = do
           (n:outfile:_) <- getArgs
           fileHandle <- openFile outfile WriteMode
           writeListToFile fileHandle . genPalin $ read n
           hClose fileHandle