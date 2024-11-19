import java.util.*;
import java.util.stream.Collectors;

public class mapdemo {

    public static void main(String[] args) {
        map romania = new map();
        System.out.println("Performing DFS...");
        BestPathResult bestResult = new BestPathResult();
        depthFirstSearch(romania.Arad, new HashSet<>(), new ArrayList<>(), 0, bestResult);
        printBestPath(bestResult); // Call to print the best path after DFS completes

        System.out.println("\nPerforming BFS...");
        breadthFirstSearch(romania.Arad);

        System.out.println("\nPerforming IDS...");
        iterativeDeepeningSearch(romania.Arad);
    }

    // Helper class to store the best path and its distance
    static class BestPathResult {
        List<city> bestPath = new ArrayList<>();
        int bestDistance = Integer.MAX_VALUE;
    }

    // Depth-First Search (DFS) with Correct Distance Calculation
    public static void depthFirstSearch(city current, Set<city> visited, List<city> path, int currentDistance, BestPathResult bestResult) {
        visited.add(current);
        path.add(current);

        // Check if the current city is the goal
        if (current.getname().equals("Bucharest")) {
            // If this path is shorter than the best found so far, update the best path
            if (currentDistance < bestResult.bestDistance) {
                bestResult.bestDistance = currentDistance;
                bestResult.bestPath = new ArrayList<>(path);
            }
        } else {
            // Explore each connection
            for (int i = 0; i < current.getconnections(); i++) {
                city nextCity = current.getcity(i);
                int edgeDistance = current.getdist(i); // Actual edge distance to the next city

                if (!visited.contains(nextCity)) {
                    depthFirstSearch(nextCity, visited, path, currentDistance + edgeDistance, bestResult);
                }
            }
        }

        // Backtrack
        path.remove(path.size() - 1);
        visited.remove(current);
    }

    // Method to print the best path after DFS completes
    private static void printBestPath(BestPathResult bestResult) {
        if (!bestResult.bestPath.isEmpty()) {
            System.out.println("Found path to Bucharest: " + bestResult.bestPath.stream()
                    .map(city::getname)
                    .collect(Collectors.joining(" -> ")) + " with distance: " + bestResult.bestDistance);
        } else {
            System.out.println("Bucharest not found.");
        }
    }

    // Breadth-First Search (BFS)
    public static void breadthFirstSearch(city start) {
        LinkedList<List<city>> queue = new LinkedList<>();
        Set<city> visited = new HashSet<>();
        visited.add(start);
        queue.add(Arrays.asList(start));

        while (!queue.isEmpty()) {
            List<city> path = queue.poll();
            city current = path.get(path.size() - 1);

            if (current.getname().equals("Bucharest")) {
                System.out.println("Found path to Bucharest: " + path.stream()
                        .map(city::getname)
                        .collect(Collectors.joining(" -> ")) + " with distance: " + getPathDistance(path));
                return;
            }

            // Explore connections
            for (int i = 0; i < current.getconnections(); i++) {
                city nextCity = current.getcity(i);
                if (!visited.contains(nextCity)) {
                    visited.add(nextCity);
                    List<city> newPath = new ArrayList<>(path);
                    newPath.add(nextCity);
                    queue.add(newPath);
                }
            }
        }
        System.out.println("Bucharest not found");
    }

    // Iterative Deepening Search (IDS)
    public static void iterativeDeepeningSearch(city start) {
        for (int depth = 0; depth < 10; depth++) {
            Set<city> visited = new HashSet<>();
            List<city> path = new ArrayList<>();
            BestPathResult bestResult = new BestPathResult();
            depthLimitedSearch(start, visited, path, 0, depth, bestResult);
            if (!bestResult.bestPath.isEmpty()) {
                System.out.println("Path found to Bucharest: " + bestResult.bestPath.stream()
                        .map(city::getname)
                        .collect(Collectors.joining(" -> ")) + " with distance: " + bestResult.bestDistance);
                return;
            }
        }
        System.out.println("Bucharest not found");
    }

    private static boolean depthLimitedSearch(city current, Set<city> visited, List<city> path, int currentDistance, int depth, BestPathResult bestResult) {
        if (depth < 0) return false;

        visited.add(current);
        path.add(current);

        if (current.getname().equals("Bucharest")) {
            if (currentDistance < bestResult.bestDistance) {
                bestResult.bestDistance = currentDistance;
                bestResult.bestPath = new ArrayList<>(path);
            }
            path.remove(path.size() - 1);
            visited.remove(current);
            return true;
        }

        if (depth > 0) {
            for (int i = 0; i < current.getconnections(); i++) {
                city nextCity = current.getcity(i);
                int edgeDistance = current.getdist(i);
                if (!visited.contains(nextCity)) {
                    boolean found = depthLimitedSearch(nextCity, visited, path, currentDistance + edgeDistance, depth - 1, bestResult);
                    if (found) {
                        //continue searching for better paths
                    }
                }
            }
        }

        path.remove(path.size() - 1);
        visited.remove(current);
        return !bestResult.bestPath.isEmpty();
    }

    private static int getPathDistance(List<city> path) {
        int distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            for (int j = 0; j < path.get(i).getconnections(); j++) {
                if (path.get(i).getcity(j).equals(path.get(i + 1))) {
                    distance += path.get(i).getdist(j);
                    break;
                }
            }
        }
        return distance;
    }
}
