#!/usr/bin/env bash
echo -n "[JWZP][$0] starting... "

echo "DEV Properties..."
if [[ $(java -jar build/libs/gradle-play-dev-all-1.0.jar props) = "db.name=postgres" ]]; then
    echo "pass"
else
    echo "fail"
    exit 1
fi
echo "PROD Properties..."
if [[ $(java -jar build/libs/gradle-play-prod-all-1.0.jar props) = "db.name=oracle" ]]; then
    echo "pass"
else
    echo "fail"
    exit 2
fi
echo "Default Properties..."
if [[ $(java -jar build/libs/gradle-play-default-all-1.0.jar props) = "db.name=local-db" ]]; then
    echo "pass"
else
    echo "fail"
    exit 3
fi
