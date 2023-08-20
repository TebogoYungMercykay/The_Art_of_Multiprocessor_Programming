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

    public static <T> StampedValue<T> max(StampedValue<T> x, StampedValue<T> y) {
        // code here
        if (x.stamp > y.stamp) {
            return x;
        } else {
            return y;
        }
    }

    // I will Come Back to This.
    public static <T> StampedValue<T> max(StampedValue<T>[] table) {
        // code here
        StampedValue<T> latest_value = table[0];
        for (int k = 0; k < table.length; k++) {
            if (table[k].stamp > table[k].stamp) {
                latest_value = table[k];
            }
        }

        return latest_value;
    }
}
