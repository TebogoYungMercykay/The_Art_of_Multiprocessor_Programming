import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class Crud {
    private volatile Queue<Info> database = new LinkedList<>();
    private volatile Queue<Info> create = new LinkedList<>();
    private volatile Queue<Boolean> read = new LinkedList<>();
    private volatile Queue<Info> update = new LinkedList<>();
    private volatile Queue<Info> delete = new LinkedList<>();

    private Bakery createLock = new Bakery(1);
    private Bakery readLock = new Bakery(1);
    private Bakery updateLock = new Bakery(1);
    private Bakery deleteLock = new Bakery(1);

    public Crud() {
        String ids[] = {"u123", "u456", "u789", "u321", "u654", "u987", "u147", "u258", "u369", "u741", "u852", "u963"};
        String names[] = {"Thabo", "Luke", "James", "Lunga", "Ntando", "Scott", "Michael", "Ntati", "Lerato", "Niel", "Saeed", "Rebecca"};
        for (int i = 0; i < 20; i++) {
            read.add(true);
            if (i < 12) create.add(new Info(ids[i], names[i], 'c'));
            if (i < 4) update.add(new Info(ids[i + 1], names[i + 1], 'u'));
            if (i < 4) delete.add(new Info(ids[i + 2], names[i + 2], 'd'));
            if (i >= 9 && i < 12) {
                update.add(new Info(ids[i], names[i], 'u'));
                delete.add(new Info(ids[i], names[i], 'd'));
            }
        }
    }

    public void start() {
        Thread createThread = new Thread(new Operator(create, database, 'c', createLock), "CreateThread");
        Thread readThread = new Thread(new Operator(read, database, 'r', readLock), "ReadThread");
        Thread updateThread = new Thread(new Operator(update, database, 'u', updateLock), "UpdateThread");
        Thread deleteThread = new Thread(new Operator(delete, database, 'd', deleteLock), "DeleteThread");

        createThread.start();
        readThread.start();
        updateThread.start();
        deleteThread.start();
    }

    public class Operator implements Runnable {
        private Queue<?> requests;
        private Queue<Info> database;
        private char operation;
        private Random random;
        private Lock lock;

        public Operator(Queue<?> requests, Queue<Info> database, char operation, Lock lock) {
            this.requests = requests;
            this.database = database;
            this.operation = operation;
            this.random = new Random();
            this.lock = lock;
        }

        @Override
        public void run() {
            while (!requests.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " is waiting for request.");
                lock.lock();
                try {
                    switch (operation) {
                        case 'c':
                            Info createRequest = (Info) ((LinkedList<?>) requests).poll();
                            database.add(createRequest);
                            System.out.println(Thread.currentThread().getName() + " success [id=" + createRequest.id + ", name=" + createRequest.name + "]");
                            break;
                        case 'r':
                            ((LinkedList<?>) requests).poll();
                            System.out.println(Thread.currentThread().getName());
                            System.out.println("—————————");
                            for (Info record : database) {
                                System.out.println("[id=" + record.id + ", name=" + record.name + ", practicals=" + record.practicals + ", assignments=" + record.assignments + "]");
                            }
                            System.out.println("—————————");
                            break;
                        case 'u':
                            Info updateRequest = (Info) ((LinkedList<?>) requests).poll();
                            boolean updated = false;
                            for (Info record : database) {
                                if (record.id.equals(updateRequest.id) && record.name.equals(updateRequest.name)) {
                                    record.practicals = updateRequest.practicals;
                                    record.assignments = updateRequest.assignments;
                                    updated = true;
                                    break;
                                }
                            }
                            if (updated) {
                                System.out.println(Thread.currentThread().getName() + " success [id=" + updateRequest.id + ", name=" + updateRequest.name + "]");
                            } else {
                                updateRequest.attempt++;
                                if (updateRequest.attempt <= 2) {
                                    ((LinkedList<Info>) requests).offer(updateRequest);
                                }
                                System.out.println(Thread.currentThread().getName() + " failed [id=" + updateRequest.id + ", name=" + updateRequest.name + "]");
                            }
                            break;
                        case 'd':
                            Info deleteRequest = (Info) ((LinkedList<?>) requests).poll();
                            boolean deleted = false;
                            for (Info record : database) {
                                if (record.id.equals(deleteRequest.id) && record.name.equals(deleteRequest.name)) {
                                    database.remove(record);
                                    deleted = true;
                                    break;
                                }
                            }
                            if (deleted) {
                                System.out.println(Thread.currentThread().getName() + " success [id=" + deleteRequest.id + ", name=" + deleteRequest.name + "]");
                            } else {
                                deleteRequest.attempt++;
                                if (deleteRequest.attempt <= 2) {
                                    ((LinkedList<Info>) requests).offer(deleteRequest);
                                }
                                System.out.println(Thread.currentThread().getName() + " failed [id=" + deleteRequest.id + ", name=" + deleteRequest.name + "]");
                            }
                            break;
                    }
                } finally {
                    lock.unlock();
                }
                try {
                    System.out.println(Thread.currentThread().getName() + " is sleeping.");
                    Thread.sleep(random.nextInt(51) + 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
