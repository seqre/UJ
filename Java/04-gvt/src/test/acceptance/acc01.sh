#!/usr/bin/env bash
echo -n "[04-GVT][$0] starting... "
mkdir my_repo
cd my_repo

if [[ $(java -jar ../build/libs/04-gvt-1.0.jar) = "Please specify command." ]]; then
    echo "pass"
else
    cd -
    echo "fail"
    exit 1
fi

cd -
