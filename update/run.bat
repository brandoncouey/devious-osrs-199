@echo off
title Update Server
xcopy /Y .\build\libs\kronos-update-server-all.jar .\
java -Xmx512m -jar kronos-update-server-all.jar
pause