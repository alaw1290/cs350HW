package hw3;

import java.util.LinkedList;

public class MM1KSimulator {
	public double lambda;
	public double serviceRate;
	public double queueMaxSize;
	public double simulationLength;
	public double currentTime;
	
	
	public MM1KSimulator(double lamb, double sr, double k, double simlen){
		lambda = lamb;
		serviceRate = sr;
		queueMaxSize = k;
		simulationLength = simlen * 2;
		
	}
	
	public double[] runMM1K(){
		currentTime = 0;
		Scheduler scheduler = new Scheduler(lambda,serviceRate, lambda*2, simulationLength);
		Logger logger = new Logger();
		Request server = null;
		LinkedList<Request> queue = new LinkedList<Request>();
		
		while(currentTime < simulationLength){
			Event currentEvent = scheduler.nextEvent(currentTime);
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
						queue.addLast(request);
					}
					else{
						//drop request
						if(currentTime > (simulationLength/2)){
							logger.dropRequest();
						}
					}
				}
			}

			else if(currentEvent.type == 1){
				// Death Event
				
				//request finished, set departure time
				server.finished(currentTime);
				
				if(currentTime > (simulationLength/2)){
					logger.logRequest(server.waitingLog(), server.queueLog());
				}
				
				if(queue.peek() != null){
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
				// Log Event (only starts after current time is past halfway point)
				if(currentTime > (simulationLength/2)){

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
		
		for(int i=0;i < queuesizes.length;i++){
			int k = queuesizes[i];
			System.out.println("Beginning MM1K Simulation: (5,0.15," + Integer.toString(k) +  ",1000) ...");
			MM1KSimulator Sim = new MM1KSimulator(5, (1/.15), k, 1000);
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
			
			System.out.println("Beginning MM1K Simulation: (6,0.15," + Integer.toString(k) +  ",1000) ...");
			Sim = new MM1KSimulator(6, (1/.15), k, 1000);
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
			
			System.out.println("Beginning MM1K Simulation: (6,0.2," + Integer.toString(k) +  ",1000) ...");
			Sim = new MM1KSimulator(6, (1/.2), k, 1000);
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
			
			System.out.println("\n");
		}
	}
}
