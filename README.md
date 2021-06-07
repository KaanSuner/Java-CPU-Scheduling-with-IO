# Java-CPU-Scheduling-with-IO
This is a CPU Scheduling program calculates average turnaround and waiting times using FCFS Algorithm.
Program gets a filename (e.g.,“input.txt”) as the command-line input and read the contents of the file. I
Provided file contains a set of processes and a set of associated CPU and I/O bursts. For example, consider the file with the following content:
### Sample Input:
The general format of a line is as follows:

 process-id:(cpu-burst1, io-burst1);(cpu-burst2, io-burst2);...(cpu-bursti, io-bursti)

* 1:(45,15);(16,20);(80,10);(40,-1)
* 2:(15,10);(60,15);(90,10);(85,20);(20,-1)
* 3:(30,15);(40,20);(5,15);(10,15);(15,-1)

In this example we have 3 processes, each process is represented in a separate line.
### Program assumes that: 
* All the jobs arrive at the same time (t=0), the order of arrival is the same as the order of process-ids
* The process never waits at the device queues and I/O starts

