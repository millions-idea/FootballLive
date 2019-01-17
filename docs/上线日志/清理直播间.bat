@echo off 
echo "Start sync"
curl -X GET  http://appliveadmin.yaboabc.com/api/live/cleanLives
echo "Stop"