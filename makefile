TARGET = MyProgram

compile:
	@mvn clean


build:
	@mvn compile

server:
	@java -jar libs/reference-server-0.1.0.jar 
 

test:
	@mvn test
# Versioning:
# 	@echo "1.1.1" >version.txt
# Version := $(shell cat version.txt)

# all:
# 	echo "current version: $(Version)"

.DEFAULT_GOAL := compile
.DEFAULT_GOAL := server
.DEFAULT_GOAL := test
# .DEFAULT_GOAL := Versioning
# .DEFAULT_GOAL := all


