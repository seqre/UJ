import os
import sys


def compare_files(f1, f2):
    result = True
    f1 = open(f1, "r")
    f2 = open(f2, "r")
    for line1 in f1:
        for line2 in f2:
            if line1 != line2:
                result = False
            break
    f1.close()
    f2.close()
    return result


os.system("java -cp build/libs/hello-di-1.0-all.jar uj.jwzp.hellodi.launchers.DaggerMain result.yml yml")
cmpResult = compare_files("result.yml", "src/test/resources/verifier.yml")

if not cmpResult:
    sys.exit(1)
