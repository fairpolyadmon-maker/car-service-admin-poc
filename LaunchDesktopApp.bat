@echo off
title Car Service Center Admin POC Desktop
cd /d "%~dp0desktop"
echo Starting Car Service Center Admin Desktop Hub...
start "" "node_modules\electron\dist\electron.exe" .
exit
