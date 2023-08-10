// Name: Selepe Sello
// Student Number: uXXXXXXXX

public class MyThread extends Thread {
    SharedResources criticalSection;
	public int resource_number = 0;
	public int  at_least_twice = 2;
	public MyThread(SharedResources CrSection){
		this.criticalSection = CrSection;
	}

	@Override
	public void run() {
		for(int i = 0; i <= at_least_twice; i++) {
			this.resource_number++;
			System.out.println(Thread.currentThread().getName() + " is Waiting to Accessed Resource: " + resource_number);
			this.criticalSection.access(this.resource_number);
		}
	}
}