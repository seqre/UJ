#!/usr/bin/env bash
echo -n "[04-GVT][$0] starting... "
cd my_repo
if [[ $(java -jar ../build/libs/04-gvt-1.0.jar add a.txt) = "Current directory is not initialized. Please use \"init\" command to initialize." ]]; then
    echo "pass non-initialized add"
else
    cd -
    echo "fail non-initialized add"
    exit 1
fi

cd -
