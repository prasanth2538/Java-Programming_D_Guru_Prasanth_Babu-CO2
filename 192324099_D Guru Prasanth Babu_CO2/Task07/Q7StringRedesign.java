public class Q7StringRedesign {
    static volatile int sink;
    static String original(int fields) {
        String s="";
        for(int i=0;i<fields;i++) s += "FIELD"+i+"|";
        return s;
    }
    static String redesigned(int fields) {
        StringBuilder s=new StringBuilder(fields*10);
        for(int i=0;i<fields;i++) s.append("FIELD").append(i).append('|');
        return s.toString();
    }
    static long benchmark(boolean revised,int fields,int tickets,int rounds) {
        long total=0;
        for(int r=0;r<rounds;r++){
            long start=System.nanoTime(); int sum=0;
            for(int t=0;t<tickets;t++)
                sum += (revised?redesigned(fields):original(fields)).length();
            total += System.nanoTime()-start; sink=sum;
        }
        return total/rounds;
    }
    public static void main(String[] args) {
        int fields=100, tickets=2000, rounds=5;
        long oldTime=benchmark(false,fields,tickets,rounds);
        long newTime=benchmark(true,fields,tickets,rounds);
        System.out.printf("Original repeated +: %.3f ms%n",oldTime/1e6);
        System.out.printf("Redesigned pre-sized StringBuilder: %.3f ms%n",newTime/1e6);
        System.out.println("Diagnosis: repeated concatenation in a loop repeatedly creates intermediate strings and copies existing characters.");
        System.out.println("Objective: build each ticket with one mutable buffer and avoid repeated intermediate strings.");
        System.out.println("Prediction: redesigned version should take less time and create fewer temporary objects for large tickets.");
        System.out.println("Trade-off: StringBuilder needs a mutable buffer; pre-sizing estimates capacity and may reserve unused space.");
        System.out.println("Small strings may show little difference; record actual repeated timings on your machine.");
    }
}