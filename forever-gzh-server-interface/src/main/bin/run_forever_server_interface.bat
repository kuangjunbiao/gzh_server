@echo on

echo "forever-server-interface on...."



set "CURRENT_DIR=%cd%"
echo "%CURRENT_DIR%"
echo "%cd%"

set tempPath=./lib
set "PATH=%PATH%;%tempPath%"
echo "%PATH%"

set MAINCLASS=com.gaoan.forever.APIServerApplication

java -jar forever-server-interface-1.0-SNAPSHOT.jar

pause


:end