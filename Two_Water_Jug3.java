// actions
// 1. fill the jug 1
// 2. fill the jug 2
// 3. empty the jug 1
// 4. empty the jug 2
// 5. transfer water from jug 1 to jug 2
// 6. transfer water from jug 2 to jug 1

import java.util.*;

class Pair{
    int j1, j2;
    List<Pair> path;

    //constructor
    public Pair(int j1, int j2){
        this.j1=j1;
        this.j2=j2;
        path = new ArrayList<>();
    }

    //constructor de khoi tao cac node re nhanh
    public Pair(int j1, int j2, List<Pair> path){
        this.j1=j1;
        this.j2=j2;
        this.path = new ArrayList<>();
        this.path.addAll(path);
        this.path.add(new Pair(j1,j2));
    }
}

public class Two_Water_Jug3{
    public static void main(String args[]){
        int jug1 = 4;
        int jug2 = 3;
        int target = 2;

        //method getPathIfPossible truyen vao tham so la dung tich 
        //nuoc chua duoc cua binh 1, binh 2 va trang thai dich can tim
        getPathIfPossible(jug1, jug2, target);
    }

    public static void getPathIfPossible(int jug1, int jug2, int target){
        // tao mang 2 chieu de truy vet cac trang thai da duyet
        boolean visited[][] = new boolean[jug1+1][jug2+1];

        // khoi tao queue de luu nhung node se duyet
        Queue<Pair> queue = new LinkedList<>();

        //  khoi tao trang thai bat dau
        // NOTE: khi them trang thai bat dau, dich vao path dung add
        Pair init = new Pair(0,0);
        init.path.add(new Pair(0,0));
        queue.offer(init);

        //loop
        while(!queue.isEmpty()){
            // xet dinh
            Pair curr = queue.poll();

            // kiem tra coi dinh da duyet chua hoac co bi tran nuoc khong (optional)
            if(visited[curr.j1][curr.j2] || curr.j1>jug1 || curr.j2>jug2) 
                continue;

            // danh dau dinh da duoc duyet
            visited[curr.j1][curr.j2]=true;

            //neu dinh dang xet la target thi luu dinh vao path
            //va in duong di
            if(curr.j1==target || curr.j2==target){
                //in duong di
                int n = curr.path.size();
                System.out.println("duong di tim duoc: ");
                for(int i=0; i<n; i++)
                    System.out.println(curr.path.get(i).j1 + "," + curr.path.get(i).j2);
                return;
            }

            // trien khai cac trang thai lan can dinh dang xet 
            // va them cac trang thai moi vao path (re nhanh)
            // them cac node moi vao queue

            // 1. fill jug 1
            queue.offer(new Pair(4, curr.j2, curr.path));

            // 2. fill jug 2
            queue.offer(new Pair(curr.j1, 3, curr.path));

            // 3. empty jug 1
            queue.offer(new Pair(0, curr.j2, curr.path));

            // 4. empty jug 2
            queue.offer(new Pair(curr.j1, 0, curr.path));

            // 5. transfer water from jug 1 to jug 2
            int emptyJug = jug2-curr.j2;
            int amountTransferred = Math.min(curr.j1, emptyJug);
            queue.offer(new Pair(curr.j1-amountTransferred, curr.j2+amountTransferred, curr.path));

            // 6. transfer water from jug 2 to jug 1
            emptyJug = jug1-curr.j1;
            amountTransferred = Math.min(emptyJug, curr.j2);
            queue.offer(new Pair(curr.j1+amountTransferred, curr.j2-amountTransferred, curr.path));
        }

        // khong tim thay duong di thoa man
        System.out.println("khong tim thay duong di thoa man");
    }
}