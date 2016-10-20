package hw3;

import java.util.*;


public class MM1Simulator {
	public double lambda;
	public double serviceRate;
	public double simulationLength;
	public double currentTime;
	
	
	public MM1Simulator(double lamb, double sr, double simlen){
		lambda = lamb;
		serviceRate = sr;
		simulationLength = simlen * 2;
		
	}
	
	public double[] runMM1(){
		currentTime = 0;
		Scheduler scheduler = new Scheduler(lambda, serviceRate, lambda*2, simulationLength);
		Logger logger = new Logger();
		Request server = null;
		LinkedList<Request> queue = new LinkedList<Request>();
		
		while(currentTime < simulationLength){
			Event currentEvent = scheduler.nextEvent();
			currentTime = currentEvent.time;
			
			
			if(currentEvent.type == 0){
				// Birth Event 

				//generate arrival of new request, set arrival time
				Request request = new Request(currentTime);
				if(server == null){
					//process request immediately, set start service time and insert death event
					server = request;
					server.serviceStart(currentTime);
					scheduler.insertDeathEvent(currentTime);
				}
				else{
					//send request to queue
					queue.addLast(request);
				}
			}

			else if(currentEvent.type == 1){
				// Death Event
				
				//request finished, set departure time
				server.finished(currentTime);
				
				if(currentTime >= (simulationLength/2)){
					logger.logRequest(server.waitingLog(), server.queueLog());
				}
				
				if(!(queue.isEmpty())){
					//process next request in the queue, set start service time and insert death event
					server = queue.removeFirst();
					server.serviceStart(currentTime);
					scheduler.insertDeathEvent(currentTime);
				}
				else{
					//empty server
					server = null;
				}
			}

			else{
				// Log Event 
				if(currentTime >= (simulationLength/2)){
					if(server == null){
						logger.writeLog(0, 0, 0);
					}
					else{
						logger.writeLog(queue.size(), queue.size() + 1, 1);
					}
				}
			}			
		}
		
	//completed simulation, return results

	return logger.logResult();
	}
	
	public static void main(String args[]){
		
		System.out.println("Beginning MM1 Simulation: (5,0.15,100000) ...");
		MM1Simulator Sim = new MM1Simulator(5, (1/0.15), 100000);
		double[] results = Sim.runMM1();
		
		//{tw,tq,w,q,p}
		System.out.format("Results:\n "
				+ "Average Wait Time (Tw): %f\n "
				+ "Average Queuing Time (Tq): %f\n "
				+ "Average # in Queue (w): %f\n "
				+ "Average # in System (q): %f\n "
				+ "Utilization (p): %f\n",
				results[0],results[1],results[2],results[3],results[4]); 
		
		System.out.println("\nBeginning MM1 Simulation: (6,0.15,100000) ...");
		Sim = new MM1Simulator(6, (1/.15), 100000);
		results = Sim.runMM1();
		
		//{tw,tq,w,q,p}
		System.out.format("Results:\n "
				+ "Average Wait Time (Tw): %f\n "
				+ "Average Queuing Time (Tq): %f\n "
				+ "Average # in Queue (w): %f\n "
				+ "Average # in System (q): %f\n "
				+ "Utilization (p): %f\n",
				results[0],results[1],results[2],results[3],results[4]);  
		
		System.out.println("\nBeginning MM1 Simulation: (6,0.2,100000) ...");
		Sim = new MM1Simulator(6, (1/.2), 100000);
		results = Sim.runMM1();
		
		//{tw,tq,w,q,p}
		System.out.format("Results:\n "
				+ "Average Wait Time (Tw): %f\n "
				+ "Average Queuing Time (Tq): %f\n "
				+ "Average # in Queue (w): %f\n "
				+ "Average # in System (q): %f\n "
				+ "Utilization (p): %f\n",
				results[0],results[1],results[2],results[3],results[4]); 
		
	}
}



