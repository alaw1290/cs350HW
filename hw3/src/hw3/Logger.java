package hw3;

public class Logger {
	private double totalTW;
	private double totalTQ;
	private double totalDropped;
	private double totalRequests;
	private double totalSuccessfulRequests;
	private double totalW;
	private double totalQ;
	private double totalP;
	private double totalLogs;
	
	public Logger(){
		totalTW = 0;
		totalTQ = 0;
		totalDropped = 0;
		totalRequests = 0;
		totalW = 0;
		totalQ = 0;
		totalP = 0;
		totalLogs = 0;
	}
	
	public void writeLog(int w, int q, int p){
		totalLogs+= 1;
		totalW += w;
		totalQ += q;
		totalP += p;
	}
	
	public void logRequest(double tw, double tq){
		totalRequests+= 1;
		totalSuccessfulRequests += 1;
		totalTW += tw;
		totalTQ += tq;
	}
	
	public void dropRequest(){
		totalRequests+= 1;
		totalDropped += 1;
	}
	
	public double[] logResult(){
		
		double tw = totalTW / totalSuccessfulRequests;
		double tq = totalTQ / totalSuccessfulRequests;
		double w = totalW / totalLogs;
		double q = totalQ / totalLogs;
		double p = totalP / totalLogs;
		double d = totalDropped / totalRequests;
		
		double[] result = {tw,tq,w,q,p,d};

		return result;
	}
}
