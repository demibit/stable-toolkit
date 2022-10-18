@ECHO OFF
git pull

cd stable-toolkit-back
start compile.bat

cd ..
cd stable-toolkit-front
start compile.bat

exit