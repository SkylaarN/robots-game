TARGET = MyProgram

Clean:
	@mvn clean
Compile:Clean
	@mvn compile


# Build:Compile
# 	@mvn package

server:
	@nohup java -jar target/toyrobot-1.0-SNAPSHOT-jar-with-dependencies.jar > server.log 2>&1 &
	@sleep 5
	@echo "Server is running on the background"

	
 

test: server
	@mvn test -e
	@mvn package


.DEFAULT_GOAL := Compile
.DEFAULT_GOAL := test



