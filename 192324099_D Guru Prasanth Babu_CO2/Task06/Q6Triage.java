import java.util.*;
class Patient {
    final String name; final int severity; final long arrival;
    Patient(String name,int severity,long arrival){this.name=name;this.severity=severity;this.arrival=arrival;}
    public String toString(){return name+"(S"+severity+")";}
}
public class Q6Triage {
    private long counter=0;
    private final PriorityQueue<Patient> queue=new PriorityQueue<>(
        Comparator.comparingInt((Patient p)->p.severity)
                  .thenComparingLong(p->p.arrival));
    void admit(String name,int severity){
        if(severity<1 || severity>5) throw new IllegalArgumentException("Severity must be 1 to 5");
        queue.add(new Patient(name,severity,counter++));
    }
    Patient next(){return queue.poll();}
    public static void main(String[] args){
        Q6Triage er=new Q6Triage();
        er.admit("Ravi",3); er.admit("Meena",1); er.admit("John",3);
        er.admit("Asha",5); er.admit("Kumar",1); er.admit("Lata",2);
        StringBuilder order=new StringBuilder(); Patient p;
        while((p=er.next())!=null) order.append(p).append(' ');
        System.out.println(order.toString().trim());
        System.out.println("Complexity: offer O(log n), poll O(log n), peek O(1).");
        System.out.println("Fairness improvement: periodically promote patients based on waiting time, with a maximum-wait guarantee.");
        System.out.println("A city hospital can handle 5,000 arrivals/day in principle; real deployment also needs persistence, concurrency control, monitoring and clinically approved triage rules.");
    }
}