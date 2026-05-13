#! /usr/bin/bash

cd ../../bin
# jar cmf META-INF/MANIFEST.MF diagsap.jar .
jar --create --file diagsap.jar --main-class org.sil.diagsap.MainApp .
cp diagsap.jar ../installer/linux/input > nul
rm diagsap.jar > nul
cd ../installer/linux
