#! /usr/bin/bash
if [ -d output ]; then
 echo "	Cleaning output"
 rm -rf output > /dev/null
fi
echo "	invoking jpackage, part 1"
# to see more details, add the --verbose option
#	--verbose \
jpackage --type app-image \
	--input input \
	--dest output \
	--name DiagSap \
	--main-jar diagsap.jar \
	--main-class org.sil.diagsap.MainApp \
	--java-options --enable-native-access=javafx.graphics \
	--java-options --enable-native-access=javafx.web \
	--java-options --enable-native-access=com.sun.jna \
	--icon input/DiagSap.png \
	--module-path jmods \
	--vendor "SIL International"
echo "	MoveResources"
./MoveResources.sh

