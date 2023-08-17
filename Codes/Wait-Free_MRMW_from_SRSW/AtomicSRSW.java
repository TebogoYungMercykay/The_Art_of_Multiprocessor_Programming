public class AtomicSRSW<T> implements Register<T> {

    // code (other member variables if any)
    long lastStamp;
    StampedValue<T> r_value; // regular SRSW timestamp-value pair
    StampedValue<T> lastRead;
    public AtomicSRSW(T init) {
        // code here
        this.lastStamp = 0;
        this.r_value = new StampedValue<T>(lastStamp, init);
        this.lastRead = new StampedValue<T>(lastStamp, init);
    }

    public T read() {
        // code here
        StampedValue<T> result = this.lastRead.max(r_value, lastRead);
        this.lastRead = result;
        return result.value;
    }

    public void write(T v) {
        // Code here
        long stamp = this.lastStamp + 1;
        this.r_value = new StampedValue<T>(stamp, v);
        this.lastStamp = stamp;
    }
}