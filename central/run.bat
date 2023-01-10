@echo off
title Central Server
xcopy /Y .\build\libs\kronos-central-server-all.jar .\
java -Xmx256m -jar kronos-central-server-all.jar
pause