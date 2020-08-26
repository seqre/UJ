#!/usr/bin/env bash
echo -n "[JWZP][$0] starting... "
if [[ $(java -jar build/libs/hello-world-1.0-SNAPSHOT.jar) = "Hello World" ]]; then
    echo "pass"
else
    echo "fail"
    exit 1
fi
