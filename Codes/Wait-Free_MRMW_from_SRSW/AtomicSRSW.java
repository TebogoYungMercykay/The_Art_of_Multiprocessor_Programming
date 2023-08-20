public class AtomicSRSW<T> implements Register<T> {

    // code (other member variables if any)
    long lastStamp;
    StampedValue<T> r_value; // regular SRSW timestamp-value pair
    StampedValue<T> lastRead;

    public AtomicSRSW(T init) {
        // code here
        this.lastStamp = 0;
        this.r_value = new StampedValue<T>(lastStamp, init);
        this.lastRead = this.r_value;
    }

    public AtomicSRSW(long stamp, T init) {
        // code here
        this.lastStamp = stamp;
        this.r_value = new StampedValue<T>(lastStamp, init);
        this.lastRead = this.r_value;
    }

    public T read() {
        // code here
        this.lastRead = StampedValue.max(r_value, lastRead);
        return this.lastRead.value;
    }

    public void write(T v) {
        // Code here
        long stamp = this.lastStamp + 1;
        this.r_value = new StampedValue<T>(stamp, v);
        this.lastStamp = stamp;
    }
}
