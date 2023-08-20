public class AtomicMRSW<T> implements Register<T> {
    // code (other member variables if any)
    int numReaders = 0;
    long lastStamp;
    private StampedValue<AtomicSRSW<T>>[][] a_table; // each entry is SRSW atomic

    @SuppressWarnings("unchecked")
    public AtomicMRSW(T init, int readers) {
        // code here
        this.numReaders = readers;
        this.a_table = new StampedValue[this.numReaders][this.numReaders];

        for (int i = 0; i < this.numReaders; i++) {
            for (int j = 0; j < this.numReaders; j++) {
                this.a_table[i][j] = new StampedValue<>(new AtomicSRSW<>(init));
            }
        }
    }

    public T read() {
        // code here
        String threadName = Thread.currentThread().getName();
        int me = Integer.parseInt(threadName.substring(threadName.length() - 1));
        StampedValue<AtomicSRSW<T>> maxReadValue = this.a_table[me][me];
        // Checking for maximum in the Column
        for (int i = 0; i < this.numReaders; i++) {
            maxReadValue = StampedValue.max(maxReadValue, this.a_table[i][me]);
        }
        // Writing the Maximum value to the Row
        for (int i = 0; i < this.numReaders; i++) {
            this.a_table[me][i] = maxReadValue;
        }
        return maxReadValue.value.read();
    }

    public void write(T v) {
        // code here
        long stamp = this.lastStamp + 1;
        this.lastStamp = stamp;
        StampedValue<AtomicSRSW<T>> value = new StampedValue<>(new AtomicSRSW<>(stamp, v));
        // Writing the Value to the Diagonal.
        for (int i = 0; i < this.numReaders; i++) {
            this.a_table[i][i] = value;
        }
    }
}
