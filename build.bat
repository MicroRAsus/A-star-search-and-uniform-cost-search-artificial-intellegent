rem change path varibles for javac and java
rem set path=C:\Program Files\Java\jdk1.8.0_161\bin
echo Building..
javac %cd%\src\*.java -d %cd%\bin
rem java -cp %cd%\bin Agent
pause