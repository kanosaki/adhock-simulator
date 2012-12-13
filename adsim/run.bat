@echo off
java -Xmx2048m -server -jar ./adsim.jar "%1" "%2"
pause > nul
