@echo off 
echo "Start sync"
curl -X GET http://appliveadmin.yaboabc.com/api/schedule/asyncScheduleList
echo "Stop"
