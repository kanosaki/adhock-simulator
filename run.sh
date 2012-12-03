#/bin/sh
if [[ $# -lt 2 ]]; then
    java -Xmx4906m -server -jar ./target/adsim.jar 100:1000.0:3:regular:1000:0.01 2
else
    java -Xmx4906m -server -jar ./target/adsim.jar $*
fi 
