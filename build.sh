echo --------------------------------------------------------------------------------------------------------
echo "                                          Running Compilation ...."
echo --------------------------------------------------------------------------------------------------------
echo
echo file_engine.reprisentation
javac -d ./bin ./src/file_engine/reprisentation/*.java
echo
echo file_engine
javac -d ./bin ./src/file_engine/*.java -classpath ./bin
# echo
# echo Interfaces
# javac -d ./bin ./src/*.java
echo
echo move these to test
cp -r ./bin/file_engine ./test
echo
echo tests
javac -d ./test ./test/src/*.java -cp ./test
echo
cd test
echo --------------------------------------------------------------------------------------------------------
echo "                                          Running Tests ...."
echo --------------------------------------------------------------------------------------------------------
echo
echo Directory
java DirectoryDriver
echo
echo Phile
java PhileDriver
echo
echo FileEngine
java FileEngineDriver
echo
echo removing test package
rm -r ./file_engine
echo
echo removing dirver classes
rm -r *.class
rm -r *.db
echo
