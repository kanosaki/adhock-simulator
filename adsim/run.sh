#/bin/sh
if [[ $# -lt 2 ]]; then
    java -Xmx2048m -server -jar ./adsim.jar 100:1000.0:3:regular:1000:0.01 1
else
    java -Xmx2048m -server -jar ./adsim.jar $*
fi 
