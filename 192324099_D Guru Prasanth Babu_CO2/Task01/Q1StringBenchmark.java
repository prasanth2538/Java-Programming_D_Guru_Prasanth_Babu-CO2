public class Q1StringBenchmark {
    static volatile int sink;
    static String buildPlus(int fields) {
        String s = "";
        for (int i = 0; i < fields; i++) s += "FIELD" + i + "|";
        return s;
    }
    static String buildBuilder(int fields) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < fields; i++) s.append("FIELD").append(i).append('|');
        return s.toString();
    }
    static String buildBuffer(int fields) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < fields; i++) s.append("FIELD").append(i).append('|');
        return s.toString();
    }
    interface Work { String run(int fields); }
    static long measure(Work w, int fields, int tickets, int repeats) {
        long total = 0;
        for (int r = 0; r < repeats; r++) {
            long start = System.nanoTime();
            int count = 0;
            for (int t = 0; t < tickets; t++) count += w.run(fields).length();
            total += System.nanoTime() - start;
            sink = count;
        }
        return total / repeats;
    }
    public static void main(String[] args) {
        int[][] workloads = {{5, 1}, {50, 10000}, {200, 1000}};
        Work[] methods = {Q1StringBenchmark::buildPlus,
                          Q1StringBenchmark::buildBuilder,
                          Q1StringBenchmark::buildBuffer};
        String[] names = {"String +", "StringBuilder", "StringBuffer"};
        for (int[] w : workloads) {
            System.out.println("\nFields per ticket=" + w[0] + ", tickets=" + w[1]);
            for (int i = 0; i < methods.length; i++) {
                long ns = measure(methods[i], w[0], w[1], 5);
                System.out.printf("%-15s average: %.3f ms%n", names[i], ns / 1_000_000.0);
            }
        }
        System.out.println("\nEstimated intermediate strings: repeated + creates many temporary strings; " +
                           "builder/buffer reuse a mutable character buffer and create the final String.");
        System.out.println("Per-ticket choice: StringBuilder (local object, no synchronization needed).");
        System.out.println("Shared log choice: StringBuffer for simple synchronized appends, or external locking.");
    }
}