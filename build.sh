#!/bin/sh
mvn assembly:single
mv ./target/adhock-simulator* ./target/adsim.jar
