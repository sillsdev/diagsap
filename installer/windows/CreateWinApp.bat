@echo off
if exist output rmdir output /S /q
REM if exist apptemp rmdir apptemp /S /q
echo 	invoking jpackage, pass 1
REM use --verbose to see more
jpackage --type app-image ^
	--input input ^
	--dest output ^
	--name DiagSap ^
	--main-jar diagsap.jar ^
	--main-class org.sil.diagsap.MainApp ^
	--java-options --enable-native-access=javafx.graphics ^
	--java-options --enable-native-access=javafx.web ^
	--java-options --enable-native-access=com.sun.jna ^
	--icon input/DiagSap.ico ^
	--module-path jmods ^
	--vendor "SIL International"
echo 	MoveResources
call MoveResources.bat
