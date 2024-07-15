TARGET = MyProgram

compile:
	@mvn clean


build:
	@mvn compile

server:
	@java -jar libs/reference-server-0.1.0.jar 
 

test:
	@mvn test

.DEFAULT_GOAL := compile
.DEFAULT_GOAL := server
.DEFAULT_GOAL := test


