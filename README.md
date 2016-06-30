# Mobile-Management-System-MMS
Mobile Management System

The fast rhythm of live demands to take information and control some situations in any time from any place, still we are in moved. So needed devices and completed information systems that can be gives solution in that wants. Below gives an example in those situations.
There is an information system which controls a situation. This system could be a control alarm system or a stock management system, or an economical management system, or any information system. This system must be extended to send information’s remote to some users or allow remote control from some users for special procedures. 
How can be builted this communication remotely? What kind of devices could be used for remote accessibility? How these devices could be programmed? The answer in these questions is “Mobile phones”. Mobile phones can send and receives data over GPRS, they are cheap and anyone have a Mobile phone. The latest generations of Mobile phones supports programming in odd programming languages. The standard programming platform which supported from the most manufacturers is JAVA and for exactly the J2ME a small edition of Java especial for micro device. Consequently mobile phones are good choice such mobile devices in a remote system. 
As extend in current information system a remote communication system has been developed with specifics procedures, in a specific part of our information system. The new system is allowed connection between the Server of information center and mobile phones. After a while other parts of our information system or other departments of our organization or other information system are needed to extend and support a remote management system. This means that must be designed and written again new applications for this communication because the first remote communication system is not compatible with new necessities.

Basic idea for MMS
The basic idea of Mobile Management System is to extend an back office system with mobile communications abilities. These extensions must be done in an easy way without being necessary to develop a new mobile system for any kind of  back office system. 
First of all the MMS sets an environment in which it accomplishes communication between mobile devices and the existing information system. Secondly the MMS gives the ability to design the signals which are exchanged between mobile devices and the existing information system. At the end the MMS give an API interface and some rules for designing and developing small applications which are connected with the existing information system with the MMS platform.

Communication schema 
The MMS is composed by two applications MMSClient application (Client applications) and MMSCentral application (Server application).

MMSClient Application
 The first application is the MMSClient application .This is a Java application written in J2ME platform. MMSClient runs on mobile phones and gives the clients the ability to communicate with the Server.

MMSCentral
 The Second application is the MMSCentral Application. This application is a middleware platform between mobile clients and the back office system. This is a Java application written in JSDK2 platform. MMSCentral runs on a PC. Firstly, it controls the communication between the clients, secondly it gives the main control to the administrator and thirdly it communicates with the back office system via extended applications.

Extended Applications 

Extended Applications are a group of small applications which undertake the role to work like a bridge between MMSCentral and back office system. These applications are necessary because the MMSCentral doesn’t know the back office system and the back office system doesn’t know how to communicate with MMSCentral. There is no restriction in the programming language for the usage of these applications. These applications must be developed/configured from the MMSCentral administrator. 
