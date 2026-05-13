@echo off
if exist installtemp rmdir installtemp /S /q
echo 	invoking jpackage, pass 2
jpackage --type exe ^
	--copyright "2021-2026 SIL International" ^
	--description "Philippine-style Word Tree Editor" ^
	--name DiagSap ^
	--install-dir "SIL\DiagSap" ^
	--resource-dir input/resources ^
	--app-image output/DiagSap ^
	--win-menu ^
	--win-shortcut ^
	--license-file License.txt ^
	--icon input/DiagSap.ico ^
	--win-upgrade-uuid 95874C31-E5D3-432C-9AD0-BE4FE31008A6 ^
	--temp installtemp ^
	--app-version %1 ^
	--file-associations diagsap.properties ^
	--vendor "SIL International" 
