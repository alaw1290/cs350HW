package hw6;

public class MyProcess extends Thread{
	private int id;
	
	public MyProcess (int i){
		id = i;
	}
	
	public void run(){
		
		for(int iter = 1; iter < 6; iter++){
			try{
				//CS begins
				System.out.printf("%d - Starting iteration %d\n",id,iter);
				System.out.printf("%d - We hold these truths to be self-evident, that all men are created equal,\n",id);
				System.out.printf("%d - that they are endowed by their Creator with certain unalienable Rights,\n",id);
				System.out.printf("%d - that among these are Life, Liberty and the pursuit of Happiness.\n",id);
				System.out.printf("%d - Done iteration %d\n",id,iter);
				//CS ends
				Thread.sleep( (long)(20.0*Math.random()));
				
			}
			catch(InterruptedException e){
				System.out.println("Thread " + Integer.toString(id) + " interrupted");
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		final int N = 2;
		MyProcess[] p = new MyProcess[N];
		for (int i = 0; i < N; i++){
			p[i] = new MyProcess(i);
			p[i].start();
		}
	}
	
}
