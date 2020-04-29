#!/bin/sh

## 使用前请先修改 UserXxx 和 KeyXxx
./gradlew clean build bintrayUpload -PdryRun=false -PbintrayUser=UserXxx -PbintrayKey=KeyXxx --info