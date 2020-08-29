#!/usr/bin/env bash
echo -n "[04-GVT][$0] starting... "
cd my_repo
if [[ $(java -jar ../build/libs/04-gvt-1.0.jar init) = "Current directory is already initialized." ]]; then
    echo "pass init"
else
    cd -
    echo "fail init"
    exit 1
fi

if [[ $(java -jar ../build/libs/04-gvt-1.0.jar history -last 1) = "0: GVT initialized." ]]; then
  echo "pass history -last 1"
else
  cd -
  echo "fail history -last 1"
  exit 2
fi

cd -
