public class StampedValue <T> {
    public long stamp;
    public T value;
    // initial value with zero timestamp
    public StampedValue(T init) {
        // code here
        this.value = init;
        this.stamp = 0;
    }
    // later values with timestamp provided
    public StampedValue(long stamp, T value) {
        // code here
        this.value = value;
        this.stamp = stamp;
    }

    public StampedValue<T> max(StampedValue<T> x, StampedValue<T> y) {
        // code here
        if (x.stamp > y.stamp) {
            return x;
        } else {
            return y;
        }
    }
}