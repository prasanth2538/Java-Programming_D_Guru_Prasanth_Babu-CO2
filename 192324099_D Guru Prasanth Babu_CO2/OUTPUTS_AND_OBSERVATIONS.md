# CSA0902 – Java Programming Assignment 2

**Student Name:** D Guru Prasanth Babu  
**Register Number:** 192324099  
**Course Code:** CSA0902 – Java Programming  
**Java Version:** JDK 11

This document contains the execution outputs shared from the student's VS Code terminal, followed by brief observations and conclusions.

---

## Q1 – String, StringBuilder and StringBuffer

### Output

```text
Fields per ticket=5, tickets=1
String +        average: 1.349 ms
StringBuilder   average: 0.012 ms
StringBuffer    average: 0.026 ms

Fields per ticket=50, tickets=10000
String +        average: 22.800 ms
StringBuilder   average: 10.078 ms
StringBuffer    average: 11.936 ms

Fields per ticket=200, tickets=1000
String +        average: 29.477 ms
StringBuilder   average: 5.543 ms
StringBuffer    average: 7.098 ms
```

### Observation

1. StringBuilder performed faster than repeated String concatenation in these tests.
2. Repeated `+` concatenation creates intermediate String objects.
3. StringBuilder and StringBuffer use mutable buffers to build strings.

### Conclusion

StringBuilder is a suitable choice for repeated string construction in a single thread. StringBuffer provides synchronized methods when thread safety is required.

---

## Q2 – ArrayList, LinkedList and ArrayDeque

### Output

```text
List size: 1000
front insert (1000 ops): ArrayList 0.559 ms | LinkedList 0.190 ms
middle get (1000 ops): ArrayList 0.103 ms | LinkedList 0.962 ms
full iteration: ArrayList 0.150 ms | LinkedList 0.259 ms
ArrayDeque add/remove first (1000 ops): 0.252 ms

List size: 10000
front insert (10000 ops): ArrayList 9.177 ms | LinkedList 0.561 ms
middle get (10000 ops): ArrayList 0.458 ms | LinkedList 72.878 ms
full iteration: ArrayList 0.599 ms | LinkedList 0.592 ms
ArrayDeque add/remove first (10000 ops): 0.684 ms

List size: 50000
front insert (20000 ops): ArrayList 70.127 ms | LinkedList 1.007 ms
middle get (20000 ops): ArrayList 0.864 ms | LinkedList 681.764 ms
full iteration: ArrayList 3.152 ms | LinkedList 2.984 ms
ArrayDeque add/remove first (20000 ops): 0.415 ms

Choice: ArrayList for frequent indexed access and end-appends; ArrayDeque for Up Next operations at both ends.
```

### Observation

1. LinkedList was faster for front insertion in these measurements.
2. ArrayList was much faster for indexed middle access.
3. Full iteration times were similar in the larger test.
4. ArrayDeque performed add/remove operations at the front efficiently.

### Conclusion

Use ArrayList when indexed access is frequent, LinkedList for suitable insertion/removal workloads, and ArrayDeque for queue or double-ended queue operations.

---

## Q3 – HashCode and Equals

### Output

```text
Broken class set size (may expose duplicate): 3
Corrected unique students: 2
[192311001 Arun, 192311002 Divya]
TreeSet size: 2
Reason: equal objects must return the same hash code. HashSet uses hashCode to find a bucket, then equals to compare candidates.
```

### Observation

1. The broken class set contains 3 entries, showing the duplicate-handling problem.
2. After correcting `equals()` and `hashCode()`, the unique student count is 2.
3. TreeSet also contains 2 students.
4. Equal objects must return the same hash code for hash-based collections to work correctly.

### Conclusion

Overriding both `equals()` and `hashCode()` consistently helps HashSet identify duplicate student records correctly.

---

## Q4 – Pharmacy Management using Maps

### Output

```text
100 medicines | 100k get: HashMap 5.257 ms, TreeMap 8.402 ms, LinkedHashMap 1.664 ms
Code order (TreeMap): [MED000001, MED000002, MED000003, MED000004, MED000005]
Insertion order (LinkedHashMap): [MED000100, MED000099, MED000098, MED000097, MED000096]
Build sorted report from HashMap by copying to TreeMap: 0.165 ms

10000 medicines | 100k get: HashMap 1.029 ms, TreeMap 10.173 ms, LinkedHashMap 0.415 ms
Code order (TreeMap): [MED000001, MED000002, MED000003, MED000004, MED000005]
Insertion order (LinkedHashMap): [MED010000, MED009999, MED009998, MED009997, MED009996]
Build sorted report from HashMap by copying to TreeMap: 8.976 ms

50000 medicines | 100k get: HashMap 1.050 ms, TreeMap 13.329 ms, LinkedHashMap 1.146 ms
Code order (TreeMap): [MED000001, MED000002, MED000003, MED000004, MED000005]
Insertion order (LinkedHashMap): [MED050000, MED049999, MED049998, MED049997, MED049996]
Build sorted report from HashMap by copying to TreeMap: 28.523 ms

Expected: HashMap average O(1) lookup; TreeMap O(log n) lookup and sorted keys; LinkedHashMap preserves insertion order.
Hashtable is legacy synchronized collection; modern code usually uses HashMap plus explicit synchronization if needed.
```

### Observation

1. HashMap provides average O(1) lookup performance.
2. TreeMap stores medicine codes in sorted order and has O(log n) lookup complexity.
3. LinkedHashMap preserves insertion order.
4. Copying HashMap data into TreeMap creates a sorted report, with the measured build time increasing as the data size increases.

### Conclusion

HashMap is useful for fast lookups, TreeMap for sorted medicine reports, and LinkedHashMap when insertion order must be preserved.

---

## Q5 – Generics and Comparator: Leaderboard

### Output

```text
[Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]

Bisons is before Panthers because both have 9 points and goal difference 4; alphabetical order puts Bisons first.
Avoid subtraction comparators; use comparingInt/reversed to avoid overflow.
```

### Observation

1. Eagles is first with 9 points and a goal difference of 6.
2. Bisons and Panthers have the same points and goal difference.
3. Bisons appears before Panthers because the final tie-breaker is alphabetical order.
4. Cobras is last with 6 points.

### Conclusion

Generics and Comparator can be used to build a type-safe leaderboard and sort teams using multiple conditions.

---

## Q6 – Patient Triage using PriorityQueue

### Output

```text
Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)
Complexity: offer O(log n), poll O(log n), peek O(1).
Fairness improvement: periodically promote patients based on waiting time, with a maximum-wait guarantee.
A city hospital can handle 5,000 arrivals/day in principle; real deployment also needs persistence, concurrency control, monitoring and clinically approved triage rules.
```

### Observation

1. Patients are removed from the priority queue according to their priority level.
2. S1 patients appear before S2, S3 and S5 patients.
3. PriorityQueue insertion (`offer`) and removal (`poll`) take O(log n), while viewing the head (`peek`) takes O(1).
4. A real triage system should use clinically approved rules and account for waiting time and fairness.

### Conclusion

PriorityQueue is useful for processing higher-priority patients before lower-priority patients. A real hospital system requires additional safety and operational controls.

---

## Q7 – String Redesign Challenge

### Output

```text
Original repeated +: 24.466 ms
Redesigned pre-sized StringBuilder: 5.483 ms
Diagnosis: repeated concatenation in a loop repeatedly creates intermediate strings and copies existing characters.
Objective: build each ticket with one mutable buffer and avoid repeated intermediate strings.
Prediction: redesigned version should take less time and create fewer temporary objects for large tickets.
Trade-off: StringBuilder needs a mutable buffer; pre-sizing estimates capacity and may reserve unused space.
Small strings may show little difference; record actual repeated timings on your machine.
```

### Observation

1. Repeated String concatenation took 24.466 ms.
2. The pre-sized StringBuilder version took 5.483 ms.
3. StringBuilder reduced the time in this test by avoiding repeated intermediate String creation.
4. Pre-sizing can reduce buffer resizing, but may reserve some unused capacity.

### Conclusion

The pre-sized StringBuilder approach was faster in this test and is useful for constructing large strings repeatedly.

---

## Overall Conclusion

This assignment demonstrates Java string handling, collection performance, object equality, Map implementations, generics, comparators, priority queues, and efficient string redesign. The benchmark timings shown are the values recorded in the student's JDK 11 execution; timings can vary across systems and repeated runs.
