package hw4;

import java.util.*;

public class Logger {
	private LinkedList<Double> totalSysTQ;
	private LinkedList<Double> totalCPUQ;
	private double pCPU;
	private double pNet;
	private double pDisk;
	private double totalLogs;
	
	
	
	public Logger(){
		// inits the logger
		totalSysTQ = new LinkedList<Double>();
		totalCPUQ = new LinkedList<Double>();
		pCPU = 0;
		pNet = 0;
		pDisk = 0;
		totalLogs = 0;
	}
	
	public void writeLog(double qCPU, double CPU, double Net, double Disk){
		// log event: store CPU Qs, sum up logs and utilization
		totalLogs+= 1;
		totalCPUQ.add(qCPU);
		pCPU += CPU;
		pNet += Net;
		pDisk += Disk;
	}
	
	public void logRequest(double tq){
		// system death event: log request system TQ 
		totalSysTQ.add(tq);
	}
	
	public double[] logResult(){
		// log result: generate utilization and confidence intervals for the system
		double pC = pCPU / totalLogs;
		double pN = pNet / totalLogs;
		double pD = pDisk / totalLogs;
		
		
		//confidence intervals for CPU Q
		Double[] qList = new Double[totalCPUQ.size()];
		int n = qList.length;
		for(int i=0; i<n; i++){
			qList[i] = totalCPUQ.remove();
		}
		double qAvg = 0;
		double qStD = 0;
		for(int i=0; i < n; i++){
			qAvg = qAvg + qList[i].doubleValue();
		}
		qAvg = qAvg / n;
		
		for(int i=0; i < n; i++){
			qStD = qStD + Math.pow((qList[i].doubleValue() - qAvg), 2);
		}
		
		qStD = Math.pow(qStD / n, 0.5);
		
		//95th Interval: Z = 1.96
		double qE95 = 1.96 * (qStD / Math.pow(n,0.5));
		//98th Interval: Z = 2.05
		double qE98 = 2.05 * (qStD / Math.pow(n,0.5));
		

		//confidence intervals for System TQ
		Double[] TqList = new Double[totalSysTQ.size()];
		n = TqList.length;
		for(int i=0; i< n; i++){
			TqList[i] = totalSysTQ.remove();
		}
		double TqAvg = 0;
		double TqStD = 0;
		for(int i=0; i < n; i++){
			TqAvg = TqAvg + TqList[i].doubleValue();
		}
		TqAvg = TqAvg / n;
		for(int i=0; i < n; i++){
			TqStD = TqStD + Math.pow((TqList[i].doubleValue() - TqAvg), 2);
		}
		TqStD = Math.pow(TqStD / n, 0.5);
		
		//95th Interval: Z = 1.96
		double TqE95 = 1.96 * (TqStD / Math.pow(n,0.5));
		//98th Interval: Z = 2.05
		double TqE98 = 2.05 * (TqStD / Math.pow(n,0.5));
		
		double [] result = {pC,pD,pN,qAvg,qE95,qE98,TqAvg,TqE95,TqE98};
		return result;
		
	}
}