@ECHO OFF
git pull
cd stable-toolkit-back
mvn clean install
mvn clean package
cd ..
cd stable-toolkit-front
npm i
npm export

exit