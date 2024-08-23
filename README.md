## Robot World
## Robot World

## Overview

# Allow Multi-Client


>>This project allows multi-Client to  effectively control robot in game environment, players can connet to the server and control robots, perform actions such movement of the robot, robot to fire shouts to the launched opponet and laying mines. The server features HTTP API layer to receive commands outside the console-based client interaction.


>>Key Features:

>>>. Multi-Client server architecture for binary or concurrenr connections.
>>>. Game logic implement on the server to manage robot state and actions.
>>>. HTTP API layer to expand command reception and intergration.
>>>. Console Client interface for direct user interation with the game environment.
>>>. Custom communication protocol ensures seamless interaction between clients and the server protocol is implemented is JSON format.


## Getting Started 

>>1. git the CJC_1 folder into the local Downloads folder

>> cd Downlods
>> git clone git@gitlab.wethinkco.de:nsilima0


## Prerequisites

>>1. Jave 11 and above
>>2. Mavern 3.8 or Above (if already installed)
>>3. Make
>>4. SQLite3 (if not already installed)


## To run the Robot Worlds Server

>> java -jar libs/reference-server-0.2.3.jar 
>>// if command-line arguments not specified, the default values will be used


>>>1. Server Commandline Arguments

>>>> -p <port-number>
>>>> // specificies the port number the server will listen on 
>>>> // Default value is 5050

>>>> -s <world-size>
>>>> // specifies the size of the world
>>>> // Default value is 1

>>>> <visibility>
>>>>// Specifies the visibility of the world
>>>>// Default value is 5 

>>>> -rp <repair-time> in seconds
>>>>// Specifies the time it takes to repair the robot shields
>>>>// Default value is 5

>>>> -rl <reload-time>
>>>>// Specifies the time it takes to reload robot weapons
>>>>// Default value is 5

>>>> -sn <set-mine-time>
>>>>// Specifies the time it takes to set a mine 
>>>>// Default value is 5

>>>> -o <x,y>
>>>>// Places an obstacles at position x,y
>>>>// Default valus is 'none', no obstacle placed

>>>> -n <x,y>
>>>>// Places a mine at position x,y
>>>>// Default value is 'none', no mine placed

>>>> pt <x,y>
>>>>// Places a pit at position x,y
>>>>// Default value is 'none', pit placed 

>>>> -or <true>
>>>>// Tells the server to generate a any number of obstacles and pits at randon position
>>>>// Default value is false,no obstacles/pits generated 

## To run the Robot Worlds Clients

>> run Activate Server and run Client classes
>> java -jar libs/reference-server-0.2.3.jar and run Client class
>>// if command-Line-arguments not specified, the default values will be used

>>1. Client CommandLine Arguments

>>>> -p <port-number>
>>>>// Specifies the port number client will create a connection to the server on
>>>>// Default value is 5050

>>>> -ip <ip-address>
>>>>// Specifies the ip address of the server for the client to connect to
>>>>// Default value is 'localhost'


## Types of Robots

>>>1. __sniper__

>>>>> A simple robot with all features shield, weapons  and survive by hiding behinh obstacles


>>>2. __operative__

>>>>> A simple robot with similar features as sniper but with less visibility by 1 as compared with sniper can load less bullets by 1 compared to sniper.

>>>3. __commando__

>>>>> A simple robot with similar features as operative but with less visibility by 1 as compared to operatives , can load less bullets by 1 compared operative

>>>4. __tank__

>>>>> A simple robot with similar features as commando but with less visibility by 1 as compared to commando, can load less bullets by 1 compared to commando

>>>5. __defender__

>>>>> A simples robot with similar features as tank but with less visibility by 1 as compared to defender, can load less bullets by 1 compared to tank



## Client Commands

# To launch a robot

>>> hal launch sniper
>>>// launches a simple robot name


# To move FORWARD by a number of steps 

>>> forward <steps>
>>>// Tells launched robot to move forward by 'steps'

# To move BACK by a number of steps

>>> back <steps>
>>>// Tells a launched robot to move back by 'steps'

# To TURN RIGHT

>>> turn right
>>>// Tells a launched robot to turn right 

# To TURN LEFT

>>> turn left
>>>// Tells a launched robot to turn left

# To get the STATE of your robot

>>> state
>>>// Gets the state of the robot

# To LOOK around the world

>>> look
>>>// looks in the cardinal directions relative to the robot and return objects in visibility 

# To RELOAD your weapons 

>>> reload
>>>// Reload the robot weapon with new annuition(shots/mines)

# To REPAIR your shields 

>>> repair
>>>// Repair the robot shields

# To FIRE a weapon at another robot 

>>> fire
>>>// Fires a shot at a specific distance
>>>// Only robots with guns can shot

# To SETMINE in your current position

>>> setmine
>>>// Sets a mine in its current position, one step forward after
>>>// Only robots have guns set a mine

# To get the current WORLD configuration

>>> world
>>>// Return the world configuration


# To QUIT from the robot world

>>> quit
>>>// Quits the game and leaves the world

# To get HELP for what commands a valid 

>>> help
>>>// Gets a list of all valid commands 


# To get HELP for what commands a valid 

>>> help
>>>// Gets a list of all commands




## Server Console Commands

# To SHOW ALL OBJECTS in the world 

>>> dump
>>>// display the current objects in the world

# To SHOW ALL ROBOTS in the world

>>> robots 
>>>// display the current robots in the world


# To REMOVE A ROBOT from the world 

>>> purge <robot-name>
>>>// Remove a robot with specified 'robot-name'


# To SAVE the current world configuration to the DATABASE 

>>> save <world-name>
>>>// restores the world in the database with the name 'world-name'

# To RESTORE a world from the DATABASE 

>>> restore <world-name>
>>>// restore the world in the database with the name 'world-name'

# To QUIT the robot world and CLOSE the world





## Team 21DBN02

>> muratsh021 - Ratshitimba Murendwa
>> tsibiya021  - Thulile Sibiya
>> wonmthiy021 - Wonderboy Mthiyane
>> gabhilto021 - Gabriel Hilton-Clarke


## Dependencies

# For testing

>> Junit version 5.6.3
>> Junit platform launcher version 1.6.3
>> Junit jupiter params version 5.7.0
>> mockito junit jupiter version 3.6.28

# For graphics

>> Java turtle version 1.0

# communication protocol

>> json version 20220320




## Team 21DBN02

>> muratsh021 - Ratshitimba Murendwa
>> tsibiya021  - Thulile Sibiya
>> wonmthiy021 - Wonderboy Mthiyane
>> gabhilto021 - Gabriel Hilton-Clarke


## Dependencies

# For testing

>> Junit version 5.6.3
>> Junit platform launcher version 1.6.3
>> Junit jupiter params version 5.7.0
>> mockito junit jupiter version 3.6.28

# For graphics

>> Java turtle version 1.0

# communication protocol

>> json version 20220320

