rmdir /s /q tmp
del sources.txt
mkdir tmp
for /r src %%i in (*.java) do @echo %%i >> sources.txt
"%JAVA_HOME%/javac" -source 1.3 -target 1.3 -d ./tmp/ -classpath ./dependencies/bdjstack.jar @sources.txt
"%JAVA_HOME%/jar" cfe bdjplus_webserver.jar com.bdjplus.webserver.WebServerModule -C tmp/ .
rmdir /s /q tmp
del sources.txt
pause