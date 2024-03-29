## Implementation of Prime Server which return whether a number is prime or not by connecting clients to it using multithreading in java
## Name: Shivkumar Subhash Kothale

-----------------------------------------------------------------------
-----------------------------------------------------------------------


Following are the commands and the instructions to run ANT on your project.
#### Note: build.xml is present in primeService/src folder.

-----------------------------------------------------------------------
## Instruction to clean:

####Command: ant -buildfile primeService/src/build.xml clean


Description: It cleans up all the .class files that were generated when you
compiled your code.

-----------------------------------------------------------------------
## Instruction to compile:

####Command: 
ant -buildfile primeService/src/build.xml all
Description: Compiles your code and generates .class files inside the BUILD folder.

-----------------------------------------------------------------------
## Instruction to run:

####Command: ant -buildfile primeService/src/build.xml run -Darg0=input.txt -Darg1=Output.txt -Darg2=errorLog.txt  -Darg3=level -Darg4=1

## Replace <fileName.txt> with real file names. For example, if the files are available in the path,
## you can run it in the following manner:



Note: Arguments accept the absolute path of the files.


