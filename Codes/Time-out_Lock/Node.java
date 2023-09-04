import java.util.Random;

public class Node extends Thread {
    private Printer printer;
    int atLeastPrint = 5;

    public Node(Printer p) {
        this.printer = p;
    }

    @Override
    public void run() {
        // Simulate sending 5 print requests
        for (int i = 1; i <= this.atLeastPrint; i++) {
            try {
                String message = generateRandomMessage(5 + (2 * i));
                System.out.println("[" + Thread.currentThread().getName() + "][Request " + i + "] printing " + message);
                this.printer.lock.lock();
                this.printer.Print(i);
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                this.printer.lock.unlock();
            }
        }
    }

    public static String generateRandomMessage(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String smallCharacters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                int index = random.nextInt(characters.length());
                char randomChar = characters.charAt(index);
                randomString.append(randomChar);
            } else {
                int index = random.nextInt(smallCharacters.length());
                char randomChar = smallCharacters.charAt(index);
                randomString.append(randomChar);
            }
        }

        return randomString.toString();
    }
}