public class DataCollection {
	private int comparisons = 0,
			exchanges = 0;

	private long startRuntime = 0,
			endRuntime = 0;

	public void addComparison(int i) { comparisons += i; }

	public int getComparisons() { return comparisons; }

	public void addExchange(int i) { exchanges += i; }

	public int getExchanges() { return exchanges; }

	public void start() { startRuntime = System.nanoTime(); }

	public void stop() { endRuntime = System.nanoTime(); }

	public long getRuntime() { return endRuntime - startRuntime; }

	public void reset() {
		comparisons = exchanges = (int) (startRuntime = endRuntime = 0);
	}
}
