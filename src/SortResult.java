public class SortResult {

    public long comparisons;
    public long swaps;
    public long timeNs;

    public SortResult(long comparisons, long swaps, long timeNs) {
        this.comparisons = comparisons;
        this.swaps = swaps;
        this.timeNs = timeNs;
    }
}
