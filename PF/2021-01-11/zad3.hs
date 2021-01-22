import System.IO
import System.Environment ( getArgs )

lineCount :: Handle -> IO()
lineCount handle = lineCount' handle 0

lineCount' :: Handle -> Int -> IO()
lineCount' handle n = do
                            eof <- hIsEOF handle
                            if eof then print n
                            else 
                                do
                                    _ <- hGetLine handle
                                    lineCount' handle (n + 1)

main :: IO ()        
main = do
           (firstArg:_) <- getArgs
           fileHandle <- openFile firstArg ReadMode
           lineCount fileHandle
           hClose fileHandle