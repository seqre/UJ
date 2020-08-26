#!/usr/bin/env bash
echo -n "[JWZP][$0] starting... "
TEST_STR=$RANDOM
if [[ $(java -jar build/libs/hello-world-1.0-SNAPSHOT.jar $TEST_STR some_dummy_param) = $TEST_STR ]]; then
    echo "pass"
else
    echo "fail"
    exit 1
fi
