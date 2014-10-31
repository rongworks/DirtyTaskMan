@ECHO OFF
echo hello
echo elevel= %ERRORLEVEL%
echo moooooooeeep
sleep 2
call cd dir
echo elevel= %ERRORLEVEL%
echo moooeeep
echo elevel= %ERRORLEVEL%

exit /B %ERRORLEVEL%