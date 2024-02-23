import java.util.*;
class Pair{
    int j1, j2; //state at each node
    List<Pair> path; //keep track of the status

    //constructor
    public Pair(int j1, int j2){
        this.j1=j1;
        this.j2=j2;
        path=new ArrayList<>(); //note
    }

    public Pair(int j1, int j2, List<Pair>path){
        this.j1=j1;
        this.j2=j2;
        //this.path=path; path hold what? address?
        this.path = new ArrayList<>();
        this.path.addAll(path);
        this.path.add(new Pair(j1,j2));
    }
}

public class Two_Water_Jug2{
    public static void main(String args[]){
        int jug1 = 4;
        int jug2 = 3;
        int target = 2;
        getPathIfPossible(jug1, jug2, target);
    }

    public static void getPathIfPossible(int jug1, int jug2, int target){
        //visted matrix
        boolean visited[][] = new boolean[jug1+1][jug2+1];

        //create an OPEN queue
        Queue<Pair> queue = new LinkedList<>();

        //initial state
        Pair init = new Pair(0,0);
        init.path.add(new Pair(0,0));
        queue.offer(init);

        //loop
        while(!queue.isEmpty()){ //still have element in open queue
            Pair cur = queue.poll();

            //skip states has visited and overflowing water states
            if(visited[cur.j1][cur.j2] || cur.j1>jug1 || cur.j2>jug2)
                continue;

            //mark state has visited
            visited[cur.j1][cur.j2]=true;
            
            //if current state is the target state
            if(cur.j1 == target || cur.j2 == target){
                if(cur.j1 == target) cur.path.add(new Pair(cur.j1,0)); //add ONLY ONE state into the path
                else cur.path.add(new Pair(0,cur.j2));

                //print the path
                int n = cur.path.size();
                System.out.println("Path of states of jugs followed is:");
                for(int i=0; i<n; i++)
                    System.out.println(cur.path.get(i).j1 + "," + cur.path.get(i).j2);
                return;
            }

            //if current state is not the target then follow below actions
            //to find the next possible states and add them into the queue
            // NOTE: neu dung path.add de them node vao thi ko re nhanh duoc ma bi tuan tu

            // 1. Fill the jug and Empty the other
            queue.offer(new Pair(4,0,cur.path));
            queue.offer(new Pair(0,3,cur.path));

            // 2. Fill the jug and let the other remain untouched
            queue.offer(new Pair(4, cur.j2, cur.path));
            queue.offer(new Pair(cur.j1, 3,  cur.path));

            // 3. Empty the jug and let the other remain untouched
            queue.offer(new Pair(0, cur.j2, cur.path));
            queue.offer(new Pair(cur.j1, 0, cur.path));

			// 4. Transfer water from one to another until
			// one jug becomes empty or until one jug
			// becomes full in this process

			// Transferring water form jug1 to jug2
            int emptyJug = jug2 - cur.j2;
            int amountTransferred = Math.min(cur.j1, emptyJug);
            queue.offer(new Pair(cur.j1-amountTransferred, cur.j2+amountTransferred, cur.path));

            // Transferring water from jug2 to jug1
            emptyJug = jug1 - cur.j1;
            amountTransferred = Math.min(emptyJug,cur.j2);
            queue.offer(new Pair(cur.j1+amountTransferred, cur.j2-amountTransferred, cur.path));
        }

        //no path is found
        System.out.println("No path is found!");
    }
}
