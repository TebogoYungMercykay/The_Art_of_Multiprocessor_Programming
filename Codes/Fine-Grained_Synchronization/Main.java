class Main {
    public static void main(String[] args) {
        Gallery g = new Gallery();
        Guard[] guards = new Guard[5];
        for (int i = 0; i < guards.length; i++) {
            guards[i] = new Guard(g);
            guards[i].start();
        }
    }
}