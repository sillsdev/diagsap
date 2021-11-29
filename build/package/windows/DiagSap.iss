;This file will be executed next to the application bundle image
;I.e. current directory will contain folder DiagSap with application files
[Setup]
AppId={{fxApplication}}
AppName=DiagSap
AppVersion=1.2.0.0
AppVerName=DiagSap version 1.2.0.0
AppPublisher=SIL International
AppComments=DiagSap
AppCopyright=Copyright © 2021 SIL International
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={pf}\DiagSap
DisableStartupPrompt=Yes
DisableDirPage=No
DisableProgramGroupPage=Yes
DisableReadyPage=No
DisableFinishedPage=No
DisableWelcomePage=No
DefaultGroupName=SIL International
;Optional License
LicenseFile=
;WinXP or above
MinVersion=0,5.1
OutputBaseFilename=DiagSap-1.2.0.0
Compression=lzma
SolidCompression=yes
PrivilegesRequired=admin
SetupIconFile=DiagSap\DiagSap.ico
UninstallDisplayIcon={app}\DiagSap.ico
UninstallDisplayName=DiagSap version 1.2.0.0
WizardImageStretch=No
WizardSmallImageFile=DiagSap-setup-icon.bmp
ArchitecturesInstallIn64BitMode=x64
ChangesAssociations=yes

[Registry]
Root: HKCR; Subkey: ".diagsap"; ValueType: string; ValueName: ""; ValueData: "DiagSapFile"; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Mime\Database\Content Type\text/x-diagsap"; ValueType: string; ValueName: "Extension"; ValueData: ".tre"; Flags: uninsdeletevalue
Root: HKCR; Subkey: "DiagSapFile"; ValueType: string; ValueName: ""; ValueData: "DiagSap file"; Flags: uninsdeletekey
Root: HKCR; Subkey: "DiagSapFile\shell\open\command"; ValueType: string; ValueName: ""; ValueData: """{app}\DiagSap"" ""%1"""

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "DiagSap\DiagSap.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "DiagSap\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\DiagSap"; Filename: "{app}\DiagSap.exe"; IconFilename: "{app}\DiagSap.ico"; Check: returnTrue()
Name: "{commondesktop}\DiagSap"; Filename: "{app}\DiagSap.exe";  IconFilename: "{app}\DiagSap.ico"; Check: returnTrue()


[Run]
Filename: "{app}\DiagSap.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\DiagSap.exe"; Description: "{cm:LaunchProgram,DiagSap}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\DiagSap.exe"; Parameters: "-install -svcName ""DiagSap"" -svcDesc ""A tool for creating Philippine style word tree diagrams."" -mainExe ""DiagSap.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\DiagSap.exe "; Parameters: "-uninstall -svcName DiagSap -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support?
  Result := True;
end;
	