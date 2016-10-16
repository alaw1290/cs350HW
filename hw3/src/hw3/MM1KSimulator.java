package hw3;

import java.util.*;

public class MM1KSimulator {
	public double lambda;
	public double serviceRate;
	public int queueMaxSize;
	public double simulationLength;
	public double currentTime;
	
	public MM1KSimulator(double lamb, double sr, int k, double simlen){
		lambda = lamb;
		serviceRate = sr;
		//K includes the size of the server (1) so queue is size k-1
		queueMaxSize = k-1;
		simulationLength = simlen * 2;
		
	}
	
	public double[] runMM1K(){
		currentTime = 0;
		Scheduler scheduler = new Scheduler(lambda,serviceRate,lambda*2,simulationLength);
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
					//if queue is smaller than max size
					if(queue.size() < queueMaxSize){
						//send request to queue
						queue.add(request);
					}
					else{
						if(currentTime >= (simulationLength/2)){
							//drop request
							logger.dropRequest();
						}
					}
				}
			}

			else if(currentEvent.type == 1){
				// Death Event
				
				//request finished, set departure time
				server.finished(currentTime);
				
				if(currentTime >= (simulationLength/2)){
					logger.logRequest(server.waitingLog(), server.queueLog());
				}
				
				if(!queue.isEmpty()){
					//process next request in the queue, set start service time and insert death event
					server = queue.remove();
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
		
		int[] queuesizes = {2,4,10};
		int n = 100000;
		
		for(int i=0;i < queuesizes.length;i++){
			int k = queuesizes[i];
			System.out.println("Beginning MM1K Simulation: (5,0.15," + Integer.toString(k) +  "," + Integer.toString(n) +  ") ...");
			MM1KSimulator Sim = new MM1KSimulator(5, (1/.15), k, n);
			double[] results = Sim.runMM1K();
			
			//{tw,tq,w,q,p,k}
			System.out.format("Results:\n "
					+ "Average Wait Time (Tw): %f\n "
					+ "Average Queuing Time (Tq): %f\n "
					+ "Average # in Queue (w): %f\n "
					+ "Average # in System (q): %f\n "
					+ "Utilization (p): %f\n "
					+ "Rejection Probability: %f\n",
					results[0],results[1],results[2],results[3],results[4],results[5]); 
			
			System.out.println("\nBeginning MM1K Simulation: (5,0.15," + Integer.toString(k) +  "," + Integer.toString(n) +  ") ...");
			Sim = new MM1KSimulator(6, (1/.15), k, n);
			results = Sim.runMM1K();
			
			//{tw,tq,w,q,p,k}
			System.out.format("Results:\n "
					+ "Average Wait Time (Tw): %f\n "
					+ "Average Queuing Time (Tq): %f\n "
					+ "Average # in Queue (w): %f\n "
					+ "Average # in System (q): %f\n "
					+ "Utilization (p): %f\n "
					+ "Rejection Probability: %f\n",
					results[0],results[1],results[2],results[3],results[4],results[5]); 
			
			System.out.println("\nBeginning MM1K Simulation: (6,0.2," + Integer.toString(k) +  "," + Integer.toString(n) +  ") ...");
			Sim = new MM1KSimulator(6, (1/.2), k, n);
			results = Sim.runMM1K();
			
			//{tw,tq,w,q,p,k}
			System.out.format("Results:\n "
					+ "Average Wait Time (Tw): %f\n "
					+ "Average Queuing Time (Tq): %f\n "
					+ "Average # in Queue (w): %f\n "
					+ "Average # in System (q): %f\n "
					+ "Utilization (p): %f\n "
					+ "Rejection Probability: %f\n",
					results[0],results[1],results[2],results[3],results[4],results[5]);
			
		}
	}
}
