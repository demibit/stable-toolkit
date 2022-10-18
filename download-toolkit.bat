@ECHO OFF
git clone https://github.com/demibit/stable-toolkit.git
cd stable-toolkit/stable-toolkit-back
mvn clean install
mvn clean package
cd ..
cd stable-toolkit-front
npm i
npm export

exit