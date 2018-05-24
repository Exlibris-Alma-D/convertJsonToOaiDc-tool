#!/bin/sh

LCP=${LCP}:lib/json2xml-converter-tool.jar
LCP=${LCP}:lib/commons-io-1.4.jar
LCP=${LCP}:lib/json-20080701.jar

${JAVA_HOME}/bin/java -cp ${LCP} convertJsontoOai.convertJsonToOai $1
STATUS=$?