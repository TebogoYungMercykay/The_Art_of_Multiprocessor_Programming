public class Guard extends Thread {
    private volatile Gallery g;
    public int person;

    public Guard(Gallery g) {
        this.g = g;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            long startTime = System.currentTimeMillis();
            person = i;
            long time = (int) Math.floor(Math.random() * (1000 + 1 - 100 + 1) + 100);
            g.enter(person, time);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            g.exit();
            if (System.currentTimeMillis() - startTime < 200) {
                try {
                    Thread.sleep(200 - (System.currentTimeMillis() - startTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
