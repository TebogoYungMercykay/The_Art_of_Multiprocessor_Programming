public class AtomicMRMW<T> implements Register<T>{
    int capacity;
    private StampedValue<AtomicMRSW<T>>[] a_table; // array of atomic MRSW registers

    @SuppressWarnings("unchecked")
    public AtomicMRMW(int capacity, T init) {
        // code here
        this.capacity = capacity;
        this.a_table = new StampedValue[this.capacity];

        for (int i = 0; i < this.capacity; i++) {
            this.a_table[i] = new StampedValue<>(new AtomicMRSW<>(init, capacity));
        }
    }

    public void write(T value) {
        // code here
        int me = ThreadID.get();
        long maxStamp = Long.MIN_VALUE;

        for (int i = 0; i < capacity; i++) {
            maxStamp = Math.max(maxStamp, a_table[i].stamp);
        }
        a_table[me] = new StampedValue<>(maxStamp + 1, new AtomicMRSW<>(value, capacity));
    }

    public T read() {
        // code here
        StampedValue<T> max = StampedValue.MIN_VALUE;

        for (int i = 0; i < capacity; i++) {
            max = StampedValue.max(max, a_table[i]);
        }
        return max.value;
    }
}
