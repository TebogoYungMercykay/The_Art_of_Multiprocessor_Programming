public class AtomicMRMW<T> implements Register<T>{
    int capacity;
    private StampedValue<AtomicMRSW<T>>[] a_table; // array of atomic MRSW registers

    @SuppressWarnings("unchecked")
    public AtomicMRMW(int capacity, T init) {
        // code here
        this.capacity = capacity;
        this.a_table = new StampedValue[this.capacity];

        for (int i = 0; i < this.capacity; i++) {
            this.a_table[i] = new StampedValue<>(new AtomicMRSW<>(init, this.capacity));
        }
    }

    public void write(T value) {
        String threadName = Thread.currentThread().getName();
        int me = Integer.parseInt(threadName.substring(threadName.length() - 1));
        StampedValue<AtomicMRSW<T>> maxStampedValue = this.a_table[0];
        // Finding the Highest TimeStamp
        for (int i = 0; i < this.capacity; i++) {
            maxStampedValue = StampedValue.max(maxStampedValue, this.a_table[i]);
        }
        // Writing the New Value into the Array
        this.a_table[me] = new StampedValue<>(maxStampedValue.stamp + 1, new AtomicMRSW<>(value, this.capacity));
    }

    public T read() {
        // code here
        // Finding the Highest TimeStamp
        StampedValue<AtomicMRSW<T>> maxStampedValue = StampedValue.max(this.a_table);
        return maxStampedValue.value.read();
    }
}
