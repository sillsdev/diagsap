@echo off
cd ..\..\bin
REM jar cmf META-INF\MANIFEST.MF pcpatreditor.jar .
jar --create --file diagsap.jar --main-class org.sil.diagsap.MainApp .
copy diagsap.jar ..\installer\windows\input > nul
del diagsap.jar > nul
cd ..\installer\windows

