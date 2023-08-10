public class Main {
    // Name: Selepe Sello
    // Student Number: uXXXXXXXX
    public static void main(String[] args) {
	    MyThread[] threads = new MyThread[5];

        SharedResources CriticalSection = new SharedResources();

        for(int i = 0; i < 5; i++)
            threads[i] = new MyThread(CriticalSection);

        for(MyThread t : threads)
            t.start();

    }
}
