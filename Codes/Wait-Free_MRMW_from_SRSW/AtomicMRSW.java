public class AtomicMRSW<T> implements Register<T> {
    // code (other member variables if any)
    int numReaders = 0;
    long lastStamp;
    private StampedValue<AtomicSRSW<T>>[][] a_table; // each entry is SRSW atomic

    @SuppressWarnings("unchecked")
    public AtomicMRSW(T init, int readers) {
        // code here
        this.numReaders = readers;
        a_table = new StampedValue[this.numReaders][this.numReaders];

        for (int i = 0; i < this.numReaders; i++) {
            for (int j = 0; j < this.numReaders; j++) {
                a_table[i][j] = new StampedValue<AtomicSRSW<T>>(init); // Must Change!!
            }
        }
    }

    public T read() {
        // code here
        int me = ThreadID.get();
        StampedValue<T> value = a_table[me][me];

        for (int i = 0; i < this.numReaders; i++) {
            value = value.max(value, a_table[i][me]);
        }

        for (int i = 0; i < this.numReaders; i++) {
            a_table[me][i] = value;
        }
    }

    public void write(T v) {
        // code here
        long stamp = lastStamp + 1;
        lastStamp = stamp;
        StampedValue<T> value = new StampedValue<>(stamp, v);

        for (int i = 0; i < this.numReaders; i++) {
            a_table[i][me] = value;
        }
    }
}
