import java.util.*;
public class Q2ListBenchmark {
    static volatile long sink;
    static long time(Runnable r) {
        long s = System.nanoTime(); r.run(); return System.nanoTime() - s;
    }
    static void test(int n) {
        List<Integer> a = new ArrayList<>(), l = new LinkedList<>();
        for (int i = 0; i < n; i++) { a.add(i); l.add(i); }
        int reps = Math.min(n, 20000), mid = n / 2;
        long x = time(() -> { for (int i=0;i<reps;i++) a.add(0, -i); });
        long y = time(() -> { for (int i=0;i<reps;i++) l.add(0, -i); });
        System.out.printf("front insert (%d ops): ArrayList %.3f ms | LinkedList %.3f ms%n",
                reps, x/1e6, y/1e6);
        a.clear(); l.clear();
        for (int i=0;i<n;i++){a.add(i);l.add(i);}
        x = time(() -> { long z=0; for(int i=0;i<reps;i++) z += a.get(mid); sink=z; });
        y = time(() -> { long z=0; for(int i=0;i<reps;i++) z += l.get(mid); sink=z; });
        System.out.printf("middle get (%d ops): ArrayList %.3f ms | LinkedList %.3f ms%n",
                reps, x/1e6, y/1e6);
        x = time(() -> { long z=0; for(int v:a) z+=v; sink=z; });
        y = time(() -> { long z=0; for(int v:l) z+=v; sink=z; });
        System.out.printf("full iteration:      ArrayList %.3f ms | LinkedList %.3f ms%n",
                x/1e6, y/1e6);
        ArrayDeque<Integer> d = new ArrayDeque<>();
        x = time(() -> { for(int i=0;i<reps;i++){d.addFirst(i); d.removeFirst();} });
        System.out.printf("ArrayDeque add/remove first (%d ops): %.3f ms%n", reps, x/1e6);
        System.out.println("Append is amortized O(1) for ArrayList and O(1) for LinkedList; end operations are suitable for deque.");
    }
    public static void main(String[] args) {
        for (int n : new int[]{1000, 10000, 50000}) {
            System.out.println("\nList size: " + n);
            test(n);
        }
        System.out.println("\nChoice: ArrayList for frequent indexed access and end-appends; ArrayDeque for Up Next operations at both ends.");
    }
}