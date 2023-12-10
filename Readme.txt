##Requirements##
1.Java version- minimum 8
2.Eclipse 2023

Our project successfully executed on the below java version:

java version "1.8.0_171"
Java(TM) SE Runtime Environment (build 1.8.0_171-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.171-b11, mixed mode)

###Eclipse setup###

1.Open Eclipse --> click on Help -->  Eclipse Marketplace.
2.Search for fx and install "e(fx)clipse" plugin. (mostly 1st result of the search).

3. We also need javaFx SDK to run/develop the javaFX applications.	
4. Go to https://gluonhq.com/products/javafx/ and download SDK for windows.
5. Unzip the folder and keep it in a folder as per your convenience.
6. Now go back to eclipse --> click on Window --> Preferences --> java --> build path --> user libraries. Or you can directly Search for "user libraries" --> create a new library with name "JavaFXLibrary".
7. Click JavaFXLibrary--> click "add external jars". Locate the jar files in your PC(step 5) and load them into it. -> apply and close.


##Other Dependency Libraries##
1.Mockito https://jar-download.com/artifacts/org.mockito
2.Junit5 
3.fontawesomefx-8.2
4.sqlite-jdbc-3.44.1.0
5.slf4j-api-1.7.36
6.hamcrest-library-2.2
7.testfx

##Project setup: Execution##

1.Load the project in Eclipse. 
2.Make sure useraccounts.db and FinancialInvestment.db are in the root folder of the project.
3.Now right click on the project "FinancialinvestmentAssistant_javaFx" from the project explorer. --> go to "properties".
4.click on "java Build Path" --> Libraries --> Add Library -->User Library --> select JavaFXLibrary(which was created in previous steps) --> apply & close.
5. we also need to include other dependency libraries (mentioned above)in the classpath.
6. Run the Main.java file.
7.	If you get an Error like: "A JNI error has occured, please check your installation try again"
	then create a Java project in eclipse and load all the project files to it.make sure directory structure is maintained.
	or Right click on the Main.java file --> Run as --> Run Configurations -->JRE -->Execution environment --> choose Java SE-1.8(jre)
	
8. If you get an Error like: "JavaFX runtime components are missing, and are required to run this application", then follow below procedure
9. Right click on the Main.java file --> Run as --> Run Configurations. 
10. Go to Arguments tab and In the "VM arguments" field, add the following (replace <path-to-javafx-lib> with the actual path((step 5 in eclipse setup)) to the JavaFX library on your system):
	--module-path <path-to-javafx-lib> --add-modules javafx.controls,javafx.fxml.
11. Re-run the Main.java file again.
12. Once the application is opened you can create a new account and login or login with: username: admin, password: admin12345.


NOTE: if you are facing any difficulties with downloading and configuring dependencies then convert the java project to maven project.
replace the pom.xml file with the pom file given along with the submission.
all dependencies will be be downloaded automatically without the need of manual effort.
if you are still facing any import errors then explicitly add libraries to the classpath.




	
