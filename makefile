TARGET = MyProgram

Clean:
	@mvn clean
Compile:Clean
	@mvn compile


# Build:Compile
# 	@mvn package

server:
	@java -jar libs/reference-server-0.1.0.jar &
	sleep 5

	
 

test: server
	@mvn test -e
	@mvn package


.DEFAULT_GOAL := Compile
.DEFAULT_GOAL := test



