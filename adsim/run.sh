#/bin/bash
if [[ $# -lt 2 ]]; then
    echo 'Argument required!!'
    exit -1
else
    java -Xmx4906m -server -jar ./target/adsim.jar $*
fi 
