import java.util.*;
class Student {
    private final String regNo, name;
    Student(String regNo, String name) { this.regNo=regNo; this.name=name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s=(Student)o;
        return Objects.equals(regNo,s.regNo);
    }
    @Override public int hashCode() { return Objects.hash(regNo); }
    @Override public String toString() { return regNo + " " + name; }
}
class BrokenStudent {
    String regNo, name;
    BrokenStudent(String r,String n){regNo=r;name=n;}
    @Override public boolean equals(Object o){
        return o instanceof BrokenStudent && regNo.equals(((BrokenStudent)o).regNo);
    }
    // Intentionally no hashCode(): demonstration only.
}
public class Q3Attendance {
    public static void main(String[] args) {
        Set<BrokenStudent> broken = new HashSet<>();
        broken.add(new BrokenStudent("101","Arun"));
        broken.add(new BrokenStudent("102","Divya"));
        broken.add(new BrokenStudent("101","Arun"));
        System.out.println("Broken class set size (may expose duplicate): " + broken.size());
        Set<Student> fixed = new HashSet<>();
        fixed.add(new Student("192311001","Arun"));
        fixed.add(new Student("192311002","Divya"));
        fixed.add(new Student("192311001","Arun"));
        System.out.println("Corrected unique students: " + fixed.size());
        System.out.println(fixed);
        TreeSet<Student> tree = new TreeSet<>(Comparator.comparing(s -> s.toString().split(" ")[0]));
        tree.addAll(fixed);
        System.out.println("TreeSet size: " + tree.size());
        System.out.println("Reason: equal objects must return the same hash code. HashSet uses hashCode to find a bucket, then equals to compare candidates.");
    }
}