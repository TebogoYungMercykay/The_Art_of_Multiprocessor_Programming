@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Gallery {
    NonBlockingList c;

    public Gallery() {
        c = new NonBlockingList();
    }

    public void enter(int person, long time) {
        while (!c.add(Thread.currentThread(), person, time)){};
    }

    public void exit() {
        while (!c.remove(Thread.currentThread())){};
    }
}
