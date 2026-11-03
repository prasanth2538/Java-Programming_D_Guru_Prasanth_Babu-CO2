import java.util.*;
class Team {
    final String name; final int points, goalDiff;
    Team(String name,int points,int goalDiff){this.name=name;this.points=points;this.goalDiff=goalDiff;}
    public String toString(){return name+"("+points+","+goalDiff+")";}
}
class Leaderboard<T> {
    private final List<T> items=new ArrayList<>();
    private final Comparator<? super T> order;
    Leaderboard(Comparator<? super T> order){this.order=order;}
    void add(T item){items.add(item);}
    List<T> ranking(){
        List<T> copy=new ArrayList<>(items);
        copy.sort(order);
        return copy;
    }
}
public class Q5LeaderboardDemo {
    public static void main(String[] args){
        Comparator<Team> byRank =
            Comparator.comparingInt((Team t)->t.points).reversed()
            .thenComparing(Comparator.comparingInt((Team t)->t.goalDiff).reversed())
            .thenComparing(t->t.name);
        Leaderboard<Team> board=new Leaderboard<>(byRank);
        board.add(new Team("Panthers",9,4));
        board.add(new Team("Eagles",9,6));
        board.add(new Team("Cobras",6,1));
        board.add(new Team("Bisons",9,4));
        System.out.println(board.ranking());
        System.out.println("Bisons is before Panthers because both have 9 points and goal difference 4; alphabetical order puts Bisons first.");
        List<Team> faulty=new ArrayList<>(Arrays.asList(
            new Team("A",Integer.MAX_VALUE,0),new Team("B",-1,0)));
        faulty.sort((a,b)->b.points-a.points); // subtraction can overflow
        System.out.println("Avoid subtraction comparators; use comparingInt/reversed to avoid overflow.");
    }
}