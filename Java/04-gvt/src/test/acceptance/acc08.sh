#!/usr/bin/env bash
echo -n "[04-GVT][$0] starting... "
cd my_repo

java -jar ../build/libs/04-gvt-1.0.jar checkout 2
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid exit code: " $0
    exit 1
fi

cmp -s b.txt ../src/test/acceptance/expected-b-v2.txt
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid b.txt content after checkout of version 2."
    exit 2
fi

java -jar ../build/libs/04-gvt-1.0.jar checkout 10

if [[ $? -ne 40 ]]; then
    cd -
    echo "fail - invalid error code after checkout of version 10."
    exit 3
fi

java -jar ../build/libs/04-gvt-1.0.jar checkout 3
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid exit code: " $0
    exit 4
fi

cmp -s b.txt ../src/test/acceptance/expected-b-v3.txt
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid b.txt content after checkout of version 3."
    exit 5
fi
rm -r -f message.out

cd -
