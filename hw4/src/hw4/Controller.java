package hw4;

import java.util.Random;
import java.io.*;

public class Controller {
	public double SystemLambda;
	public double CPUsr;
	public double Netsr;
	public double Disksr;
	public double simulationLength;
	public double currentTime;
	private Random rand;
	private int f;
	
	public Controller(double lamb, double cTS, double nTS, double dTS, double simlen, int flag){
		//Initializes the parameters for the system components
		SystemLambda = lamb;
		CPUsr = 1.0/cTS;
		Netsr = 1.0/nTS;
		Disksr = 1.0/dTS;
		simulationLength = simlen;
		f = flag;
	}
	
	public double[] run() throws FileNotFoundException {
		//runs simulation of network queues
		currentTime = 0;
		Scheduler scheduler = new Scheduler(SystemLambda, SystemLambda*2, CPUsr, Netsr, Disksr, simulationLength,f);
		Logger logger = new Logger();
		rand = new Random();
		
		//CPU Queue Deaths IDs: 21(A), 22(B)
		MM2 CPU = new MM2();
		
		//Network Queue Death ID: 3
		MM1 Network = new MM1();
		
		//Disk Queue Death ID: 4
		MM1 Disk = new MM1();
		
		try{
			File output = new File("CPUQs.txt");
			PrintWriter printWriter = new PrintWriter(output);
			while(currentTime < simulationLength){
				Event currentEvent = scheduler.nextEvent();
				currentTime = currentEvent.time;
				
				//currentEvent.type = 0: Log Event
				if(currentEvent.type == 0){
					//utilization of CPU
					double c = 0;
					if(CPU.serverA != null){
						c = c + 0.5;
					}
					if(CPU.serverB != null){
						c = c + 0.5;
					}
					
					//utilization of Net
					double n = 0;
					if(Network.server != null){
						n = 1;
					}
					
					//utilization of Disk
					double d = 0;
					if(Disk.server != null){
						d = 1;
					}	
					logger.writeLog(CPU.queue.size() + (c*2), c, n, d);
					printWriter.format("%f,%f\n",currentTime,(CPU.queue.size() + (c*2)));
				}
				
				
				
				//currentEvent.type = 1: System Birth Event
				if(currentEvent.type == 1){
					Request request = new Request(currentTime);
					
					//status 0:in queue, 1: in server A, 2: in server B
					int status = CPU.birth(request);
					if(status == 1){
						scheduler.insertDeathEvent(currentTime,21);
					}
					else if(status == 2){
						scheduler.insertDeathEvent(currentTime,22);
					}
					
				}
				
				
				
				//currentEvent.type = 21: CPU A Death Event
				if(currentEvent.type == 21){
					Request request = CPU.death1();
					//check if death event needs to be generated
					if(CPU.serverA != null){
						scheduler.insertDeathEvent(currentTime,21);
					}
					
					//check where request will now go
					double random = rand.nextDouble();
					
					//p(leaves system) = 0.5
					if(random <= 0.5){
						request.SystemExit(currentTime);
						logger.logRequest(request.queueLog());
					}
					//p(enters net) = 0.4
					else if(random <= 0.9){
						int status = Network.birth(request);
						if(status == 1){
							scheduler.insertDeathEvent(currentTime,3);
						}
					}
					//p(enters disk) = 0.1
					else{
						int status = Disk.birth(request);
						if(status == 1){
							scheduler.insertDeathEvent(currentTime,4);
						}
					}
				}
				
				
				
				//currentEvent.type = 22: CPU B Death Event
				if(currentEvent.type == 22){
					Request request = CPU.death2();
					//check if death event needs to be generated
					if(CPU.serverB != null){
						scheduler.insertDeathEvent(currentTime,22);
					}
					
					//check where request will now go
					double random = rand.nextDouble();
					
					//p(leaves system) = 0.5
					if(random <= 0.5){
						request.SystemExit(currentTime);
						logger.logRequest(request.queueLog());
					}
					
					//p(enters net) = 0.4
					else if(random <= 0.9){
						int status = Network.birth(request);
						if(status == 1){
							scheduler.insertDeathEvent(currentTime,3);
						}
					}
					//p(enters disk) = 0.1
					else{
						int status = Disk.birth(request);
						if(status == 1){
							scheduler.insertDeathEvent(currentTime,4);
						}
					}
				}
				
				
				
				//currentEvent.type = 3: Network Death Event
				if(currentEvent.type == 3){
					Request request = Network.death();
					//check if death event needs to be generated
					if(Network.server != null){
						scheduler.insertDeathEvent(currentTime,3);
					}
					
					//Network requests go straight to CPU
					int status = CPU.birth(request);
					if(status == 1){
						scheduler.insertDeathEvent(currentTime,21);
					}
					else if(status == 2){
						scheduler.insertDeathEvent(currentTime,22);
					}
				}
				
				
				
				//currentEvent.type = 4: Disk Death Event
				if(currentEvent.type == 4){
					Request request = Disk.death();
					//check if death event needs to be generated
					if(Disk.server != null){
						scheduler.insertDeathEvent(currentTime,4);
					}
					
					//check where request will now go
					double random = rand.nextDouble();
					
					//p(goes to CPU) = 0.5
					if(random <= 0.5){
						int status = CPU.birth(request);
						if(status == 1){
							scheduler.insertDeathEvent(currentTime,21);
						}
						else if(status == 2){
							scheduler.insertDeathEvent(currentTime,22);
						}
					}
					else{
						int status = Network.birth(request);
						if(status == 1){
							scheduler.insertDeathEvent(currentTime,3);
						}
					}
				}
				
				
			}
			printWriter.close();
		}
		catch(FileNotFoundException ex){
			System.out.println("Error: cannot write CPUqs to file");
		}
		return logger.logResult();
	}
	
	
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("Beginning Network Queues Simulation ...");
		//							  lamb,  CPU,  net,  disk trials flag (0: question 2 1: question 3)
		Controller Sim = new Controller(40, 0.02, 0.025, 0.1, 1000, 0);
		System.out.println("Printing CPU Q till T=100:\n");
		try{
			double[] results = Sim.run();
			System.out.format("\nResults:\n "
					+ "CPU Utilization (P): %f\n "
					+ "Disk Utilization (P): %f\n "
					+ "Network Utilization (P): %f\n "
					+ "95th CI for CPU Q: %f +- %f\n "
					+ "98th CI for CPU Q: %f +- %f\n "
					+ "95th CI for System TQ: %f +- %f\n "
					+ "98th CI for System TQ: %f +- %f\n ",
					results[0],results[1],results[2],results[3],results[4],results[3],results[5],results[6],results[7],results[6],results[8]);
			
			Sim = new Controller(40, 0.02, 0.025, 0.1, 1000, 1);
			results = Sim.run();
			System.out.format("\nResults:\n "
					+ "CPU Utilization (P): %f\n "
					+ "Disk Utilization (P): %f\n "
					+ "Network Utilization (P): %f\n "
					+ "95th CI for CPU Q: %f +- %f\n "
					+ "98th CI for CPU Q: %f +- %f\n "
					+ "95th CI for System TQ: %f +- %f\n "
					+ "98th CI for System TQ: %f +- %f\n ",
					results[0],results[1],results[2],results[3],results[4],results[3],results[5],results[6],results[7],results[6],results[8]);
			
			Sim = new Controller(45, 0.02, 0.025, 0.1, 1000, 0);
			results = Sim.run();
			System.out.format("\nResults:\n "
					+ "CPU Utilization (P): %f\n "
					+ "Disk Utilization (P): %f\n "
					+ "Network Utilization (P): %f\n "
					+ "95th CI for CPU Q: %f +- %f\n "
					+ "98th CI for CPU Q: %f +- %f\n "
					+ "95th CI for System TQ: %f +- %f\n "
					+ "98th CI for System TQ: %f +- %f\n ",
					results[0],results[1],results[2],results[3],results[4],results[3],results[5],results[6],results[7],results[6],results[8]);
		}
		
		
		catch(FileNotFoundException ex){
			System.out.println("broken");
		}
		
	}

}
