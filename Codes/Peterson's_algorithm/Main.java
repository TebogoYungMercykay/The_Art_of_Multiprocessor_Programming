// Name: Selepe Sello
// Student Number: uXXXXXXXX
/*
    Peterson's algorithm is a concurrent programming algorithm for mutual exclusion that allows two
    processes to share a single-use resource without conflict, using only shared memory for communication.
    It was formulated by Gary L. Peterson in 1981. The algorithm was later generalized for more than two processes.

    Two processes use a respective "flag" to indicate that they want to enter a critical
    section. However using just that could lead to a deadlock. So they use a tie-breaker "turn" to
    indicate whose turn it is to wait. So, each process says it wants to enter Critical Section but also that
    it is its turn to wait. In the end, a process  only waits if the other process wants to enter
    Critical Section as well as it is its own turn to wait. This tie-breaker prevents deadlock.
*/

class Main {
    static boolean[] flag = {false, false};
    static int turn = 0; // Whose turn to enter Critical Section
    static int number_of_loops = 4;
    // flag: ith process wants to enter Critical Section?

    static Thread process(int i) {
        return new Thread(() -> {
            int j = (1 - i);
            for (int num = 0; num < number_of_loops; num++) {
                log(i + ": want Critical Section"); // LOCK
                flag[i] = true; // 1. I want to enter Critical Section.
                turn = j;       // 2. Its the other process' turn.
                while (flag[j] && turn == j){
                    Thread.yield(); // 3. I wait if you want too and your turn.
                }
                log(i + ": in Critical Section" + num);
                sleep(1000 * Math.random()); // 4. I enter Critical Section (sleep).

                log(i + ": done Critical Section"); // UNLOCK
                flag[i] = false; // 5. I am done with Critical Section.
            }
        });
    }

    public static void main(String[] args) {
        try {
            log("Starting 2 processes (threads) ...");
            Thread p0 = process(0);
            Thread p1 = process(1);
            p0.start();
            p1.start();
            p0.join();
            p1.join();
        } catch (InterruptedException e) {
            // Swallow Exception
        }
    }

    static void sleep(double t) {
        try {
            Thread.sleep((long)t);
        } catch (InterruptedException e) {
            // Swallow Exception
        }
    }

    static void log(String x) {
        System.out.println(x);
    }
}