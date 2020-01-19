# Process-and-Resource-Schedular
A java 8 based process and resource manager

Read this before you start running this program:

My Program is using JAVA 8

The program consists of 6 Java class files.

First one: Shell
	Shell contains the main function of this program. How to run this program? Just change the path in the FileReader at line 16. 
	This file calls Manager file to run different jobs differ by inputs.

Second one: Manager
	Manager is the process manager file. 
	Variables:
    1.	PCB is an array of process and each index is its pid.
    2.	RCB is an array of resource and each index is its rid.
    3.	RL is a class.
    4.	WL is also a class.
    5.	current is a Process type stands for current process that is running.
    6.	totalProcess is an integer stands for total process that is running.
	Functions:
    1.	It has a constructor which takes no input and it is been called when input “in.”
    2.	Create function: called when input “cr.”
    3.	Destroy function: called when input “de.”
    4.	Request function: called when input “rq.”
    5.	Release function: called when input “rl.”
    6.	Timeout function: called when input “to.”
    7.	Private functions:
    a.	Schedular function: called after each function above besides constructor.
    b.	DestroyP function: called when destroying the children process.
    c.	WLorRL function: called when needing to check whether this process is in waitlist or ready list.

Third one: Process
	Process contains variable and functions that a process should have. Every variable is private, other class needs to call getter and setter to gain access to it.

Fourth one: Resource
	Resource contains variables and functions that a resource should have. It has four type and its type is defined by the rid.
	One variable that is different from the instruction is total which stands for total units in this resource that is available.

Fifth one: ReadyList
	ReadyList contains 3 variables which stands for 3 different priority levels and they are private, which needs to be accessed by getter function that takes priority as input. Also, it contains two functions insert and delete in order to modify the list.

Sixth one: WaitList
	Some construction as ReadyList.


	


	
