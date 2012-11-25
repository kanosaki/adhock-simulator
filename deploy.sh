#!/bin/sh
mvn assembly:single
mv ./target/adhock-simulator* ./adsim/adsim.jar
zip -r adsim.zip adsim/
