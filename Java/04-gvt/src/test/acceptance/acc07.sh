#!/usr/bin/env bash
echo -n "[04-GVT][$0] starting... "
cd my_repo

echo "xyz" > b.txt

java -jar ../build/libs/04-gvt-1.0.jar commit
if [[ $? -ne 50 ]]; then
    cd -
    echo "fail - invalid exit code for empty commit "
    exit 1
fi

java -jar ../build/libs/04-gvt-1.0.jar commit b.txt -m "Some b-change" > message.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid exit code for commit "
    exit 2
fi

cmp -s message.out ../src/test/acceptance/expected07.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid messages after commit."
    exit 3
fi
rm -r -f message.out

if [[ $(java -jar ../build/libs/04-gvt-1.0.jar history -last 1) = "3: Committed file: b.txt" ]]; then
  echo "pass version -last 1"
else
  cd -
  echo "fail version -last 1"
  exit 4
fi

java -jar ../build/libs/04-gvt-1.0.jar version > message.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid exit code: " $0
    exit 5
fi

cmp -s message.out ../src/test/acceptance/commit3-expected.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid version result."
    exit 6
fi
rm -r -f message.out

cd -
