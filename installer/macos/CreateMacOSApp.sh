#! /bin/bash
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
	--add-modules jdk.management.jfr,java.rmi,jdk.jdi,jfx.incubator.input,java.xml,jdk.xml.dom,java.datatransfer,jdk.httpserver,javafx.base,java.desktop,java.security.sasl,jdk.zipfs,java.base,jfx.incubator.richtext,jdk.javadoc,jdk.management.agent,jdk.jshell,java.sql.rowset,jdk.sctp,jdk.jsobject,javafx.swing,jdk.unsupported,java.smartcardio,java.security.jgss,jdk.nio.mapmode,java.compiler,javafx.graphics,javafx.fxml,jdk.dynalink,jdk.unsupported.desktop,javafx.media,jdk.accessibility,jdk.security.jgss,jdk.incubator.vector,java.sql,javafx.web,java.xml.crypto,java.logging,java.transaction.xa,jdk.jfr,jdk.internal.md,jdk.net,java.naming,javafx.controls,jdk.internal.ed,java.prefs,java.net.http,jdk.compiler,jdk.internal.opt,jdk.jconsole,jdk.attach,jdk.internal.le,java.management,jdk.jdwp.agent,jdk.internal.jvmstat,java.instrument,jdk.management,jdk.security.auth,java.scripting,com.azul.tooling,jdk.jartool,java.management.rmi,jdk.localedata \
	--jlink-options "--include-locales=en,fr,es" \
	--icon input/DiagSap.icns \
	--module-path jmods \
	--vendor "SIL International"
#	--vendor "SIL International" \
#	--mac-sign \
#	--mac-signing-key-user-name "Developer ID Application: Summer Institute of Linguistics, Inc (SIL) (3YE4W86L3G)"
echo "	MoveResources"
./MoveResources.sh

