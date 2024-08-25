TARGET = MyProgram

Clean:
	@mvn clean
Compile:Clean
	@mvn compile


# Build:Compile
# 	@mvn package

server:
	@java -jar libs/reference-server-0.1.0.jar  &
	@sleep 10
	@echo "Server is running in the background"


test: server
	@mvn test -e
	@mvn package
	@echo "Killing the server..."
	@pkill -f 'java -jar libs/reference-server-0.1.0.jar'
	@echo "Server has been terminated."
	@java -jar target/toyrobot-1.0-SNAPSHOT-jar-with-dependencies.jar &
	@echo "Server is running on the background"
	@mvn test -e



.DEFAULT_GOAL := Compile
.DEFAULT_GOAL := test



