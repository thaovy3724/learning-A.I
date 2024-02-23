// 1: represent the left bank of river
// 0: represent the right bank of river

// start state: (1,1,1,1)
// goal state: (0,0,0,0)

// actions:
// * From Left to Right *
// 1. farmer go alone
// 2. farmer + fox
// 3. farmer + goat
// 4. farmer + bean

// * From Right to Left *
// 1. farmer go alone
// 2. farmer + fox
// 3. farmer + goat
// 4. farmer + bean

// forbid: fox and goat alone, goat and bean alone

import java.util.*;

class State{
    int farmer;
    int fox;
    int goat;
    int bean;

    ArrayList<State> path;

    public State(int farmer, int fox, int goat, int bean){
        this.farmer = farmer;
        this.fox = fox;
        this.goat = goat;
        this.bean = bean;
        path = new ArrayList<>();
    }

    public State(int farmer, int fox, int goat, int bean, ArrayList<State> path){
        this.farmer = farmer;
        this.fox = fox;
        this.goat = goat;
        this.bean = bean;
        this.path = new ArrayList<>();
        this.path.addAll(path);
        this.path.add(new State(farmer, fox, goat, bean));
    }
}

public class Farmer_Cross_River{
    public static void main(String args[]){
        getPathIfPossible();
    }

    public static void getPathIfPossible(){
        // visited matrix
        boolean visited[][][][] = new boolean[2][2][2][2];

        //queue
        Queue<State> queue = new LinkedList<>();

        // initial state
        State init = new State(1,1,1,1);
        init.path.add(new State(1,1,1,1));
        queue.offer(init);

        int farmerMove=1;

        //loop
        while(!queue.isEmpty()){
            State curr = queue.poll();

            // if current state has visited or states that violate conditions
            if(visited[curr.farmer][curr.fox][curr.goat][curr.bean] ||
            (curr.farmer==1 && curr.fox==0 && curr.goat==0) ||
            (curr.farmer==0 && curr.fox==1 && curr.goat==1) ||
            (curr.farmer==1 && curr.bean==0 && curr.goat==0) ||
            (curr.farmer==0 && curr.bean==1 && curr.goat==1))
                continue;

            // if state hasn't visited, mark it as visited
            visited[curr.farmer][curr.fox][curr.goat][curr.bean] = true;

            // if current state is the goal
            if(curr.farmer==0 && curr.fox==0 && curr.goat==0 && curr.bean==0){
                //print path
                int n = curr.path.size();
                System.out.println("The possible path is: ");
                for(int i=0; i<n; i++)
                    System.out.println(curr.path.get(i).farmer + "," + curr.path.get(i).fox + "," 
                    + curr.path.get(i).goat + "," + curr.path.get(i).bean);
                return;
            }

            // states after takes some actions
            // note: the farmer is at same side with the bank
            // 1. farmer go alone
            if(curr.farmer==1) farmerMove = 0;
            else farmerMove = 1;
            queue.offer(new State(farmerMove,curr.fox, curr.goat, curr.bean, curr.path));

            // 2. farmer + fox
            if(curr.farmer == curr.fox)
                queue.offer(new State(farmerMove,farmerMove, curr.goat, curr.bean, curr.path));

            // 3. farmer + goat
            if(curr.farmer == curr.goat)
                queue.offer(new State(farmerMove,curr.fox, farmerMove, curr.bean, curr.path));

            // 4. farmer + bean
            if(curr.farmer == curr.bean)
                queue.offer(new State(farmerMove,curr.fox, curr.goat, farmerMove, curr.path));
        }
        // not find a possible path
        System.out.println("cannot find a possible path");
    }
}