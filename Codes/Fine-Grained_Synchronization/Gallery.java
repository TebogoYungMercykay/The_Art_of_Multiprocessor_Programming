@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Gallery {
    FineList c;

    public Gallery() {
        c = new FineList();
    }

    public void enter(int person, long time) {
        while (!c.add(Thread.currentThread(), person, time)){};
    }

    public void exit() {
        while (!c.remove(Thread.currentThread())){};
    }
}
