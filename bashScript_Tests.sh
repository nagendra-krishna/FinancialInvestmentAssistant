#!/bin/bash

# Paths to main and test compiled classes
MAIN_CLASSES="C:\Users\krish\Desktop\ECWorkSpace\FinancialInvestmentAssitant_javaFX\bin\main\java\app"
TEST_CLASSES="C:\Users\krish\Desktop\ECWorkSpace\FinancialInvestmentAssitant_javaFX\src\nka15\krishna\WhiteBoxTests"

# Classpath including the JUnit standalone JAR and main/test classes
CLASS_PATH="$MAIN_CLASSES;$TEST_CLASSES;C:\Users\krish\Desktop\ECWorkSpace\FinancialInvestmentAssitant_javaFX\junit-platform-console-standalone-1.10.1.jar"

# Compiling main Java files
javac -d $MAIN_CLASSES -cp $CLASS_PATH src/main/java/*.java

# Compiling test Java files
javac -d $TEST_CLASSES -cp $CLASS_PATH src/test/java/*.java

# Running tests and generating JaCoCo coverage report
java -cp $CLASS_PATH -javaagent:path/to/jacoco-0.8.11/lib/jacocoagent.jar -jar path/to/junit-platform-console-standalone-1.10.1.jar --class-path $CLASS_PATH --scan-class-path

# Generating the report from JaCoCo data
java -jar path/to/jacoco-0.8.11/lib/jacococli.jar report jacoco.exec --classfiles $MAIN_CLASSES --sourcefiles src/main/java --html report
