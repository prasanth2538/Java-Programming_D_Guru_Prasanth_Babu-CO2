import java.util.*;
public class Q4PharmacyMaps {
    static void run(int n) {
        Map<String,Integer> hash = new HashMap<>();
        Map<String,Integer> tree = new TreeMap<>();
        Map<String,Integer> linked = new LinkedHashMap<>();
        for(int i=0;i<n;i++){
            String code=String.format("MED%06d", n-i);
            hash.put(code, 100+i); tree.put(code,100+i); linked.put(code,100+i);
        }
        String key=String.format("MED%06d", n);
        long s=System.nanoTime();
        for(int i=0;i<100000;i++) hash.get(key);
        long hashTime=System.nanoTime()-s;
        s=System.nanoTime();
        for(int i=0;i<100000;i++) tree.get(key);
        long treeTime=System.nanoTime()-s;
        s=System.nanoTime();
        for(int i=0;i<100000;i++) linked.get(key);
        long linkedTime=System.nanoTime()-s;
        System.out.printf("%d medicines | 100k get: HashMap %.3f ms, TreeMap %.3f ms, LinkedHashMap %.3f ms%n",
                n,hashTime/1e6,treeTime/1e6,linkedTime/1e6);
        System.out.println("Code order (TreeMap): " + firstKeys(tree));
        System.out.println("Insertion order (LinkedHashMap): " + firstKeys(linked));
        s=System.nanoTime(); new TreeMap<>(hash); long sortTime=System.nanoTime()-s;
        System.out.printf("Build sorted report from HashMap by copying to TreeMap: %.3f ms%n",sortTime/1e6);
    }
    static String firstKeys(Map<String,Integer> m){
        List<String> keys=new ArrayList<>(m.keySet());
        return keys.subList(0,Math.min(5,keys.size())).toString();
    }
    public static void main(String[] args){
        for(int n:new int[]{100,10000,50000}) run(n);
        System.out.println("Expected: HashMap average O(1) lookup; TreeMap O(log n) lookup and sorted keys; LinkedHashMap preserves insertion order.");
        System.out.println("Hashtable is legacy synchronized collection; modern code usually uses HashMap plus explicit synchronization if needed.");
    }
}