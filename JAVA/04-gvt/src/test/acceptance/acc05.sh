#!/usr/bin/env bash
echo -n "[04-GVT][$0] starting... "
cd my_repo

echo "abc" > a.txt

java -jar ../build/libs/04-gvt-1.0.jar add a.txt > message.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid exit code: " $0
    exit 1
fi

cmp -s message.out ../src/test/acceptance/expected05.out
if [[ $? -ne 0 ]]; then
    cd -
    echo "fail - invalid messages after adding."
    exit 2
fi
rm -r -f message.out

if [[ $(java -jar ../build/libs/04-gvt-1.0.jar history -last 1) = "1: Added file: a.txt" ]]; then
  echo "pass version -last 1"
else
  cd -
  echo "fail version -last 1"
  exit 3
fi

cd -
