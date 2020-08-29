#!/usr/bin/env bash
echo -n "[04-GVT][$0] starting... "
cd my_repo

java -jar ../build/libs/04-gvt-1.0.jar detach > message.out
if [[ $? -ne 30 ]]; then
    cd -
    echo "fail - invalid exit code after empty detach: " $0
    exit 1
fi

cmp -s message.out ../src/test/acceptance/expected09-01.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid message after empty detach."
    exit 2
fi
rm -r -f message.out

java -jar ../build/libs/04-gvt-1.0.jar detach c.txt> message.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid exit code after non-added file detach: " $0
    exit 3
fi

cmp -s message.out ../src/test/acceptance/expected09-02.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid messages after non-added file detach."
    exit 4
fi
rm -r -f message.out

java -jar ../build/libs/04-gvt-1.0.jar detach a.txt -m "a file is not needed"> message.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid exit code after added file detach: " $0
    exit 5
fi
cmp -s message.out ../src/test/acceptance/expected09-03.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid messages after added file detach."
    exit 6
fi
rm -r -f message.out

if [[ $(java -jar ../build/libs/04-gvt-1.0.jar history -last 1) = "4: Detached file: a.txt" ]]; then
  echo "pass version -last 1"
else
  cd -
  echo "fail version -last 1"
  exit 7
fi

java -jar ../build/libs/04-gvt-1.0.jar version > message.out

cmp -s message.out ../src/test/acceptance/commit4-expected.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid version result."
    exit 8
fi

rm -r -f message.out
cd -
