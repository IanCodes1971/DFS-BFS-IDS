import java.util.ArrayList;

public class city {
    private String name;
    private int numconnections = 0;
    private ArrayList<city> nextcity = new ArrayList<>();
    private ArrayList<Integer> distance = new ArrayList<>();
    private int SLD;

    public int depth;
    public city camefrom;
    
    // variable to track visited status
    private boolean visited;

    public city(String n, int s) {
        this.name = n;
        this.SLD = s;
        this.visited = false; // Initially, all cities are unvisited
    }

    // Method to add a connection
    public void addconnection(city conn, int dist) {
        numconnections++;
        nextcity.add(conn);
        distance.add(dist);
    }

    // Method to reset visited status
    public void resetVisited() {
        this.visited = false;
    }

    // Method to mark the city as visited
    public void markVisited() {
        this.visited = true;
    }

    // Method to check if the city has been visited
    public boolean isVisited() {
        return this.visited;
    }

    // Getters
    public int getSLD() { return this.SLD; }
    public int getconnections() { return numconnections; }
    public city getcity(int index) { return nextcity.get(index); }
    public int getdist(int index) { return distance.get(index); }
    public String getname() { return this.name; }
}
