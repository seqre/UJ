#!/usr/bin/env bash
echo -n "[JWZP][$0] starting... "
if [[ $(java -jar build/libs/gradle-play-default-all-1.0.jar) = "Hello world" ]]; then
    echo "pass"
else
    echo "fail"
    exit 1
fi
