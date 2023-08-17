public interface Register<T> {
    T read();
    void write(T v);
    int filterThread(String thread);
}