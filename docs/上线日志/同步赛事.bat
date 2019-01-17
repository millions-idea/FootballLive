@echo off 
echo "Start sync"
curl -X GET http://appliveadmin.yaboabc.com/api/game/asyncGameList
echo "Stop"
pause