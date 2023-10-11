@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Gallery {
    LazyList c;

    public Gallery() {
        c = new LazyList();
    }

    public void enter(int person, long time) {
        while (!c.add(Thread.currentThread(), person, time)){};
    }

    public void exit() {
        while (!c.remove(Thread.currentThread())){};
    }
}
