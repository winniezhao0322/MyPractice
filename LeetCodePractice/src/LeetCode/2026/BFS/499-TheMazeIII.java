package LeetCode.Hard;

import java.util.PriorityQueue;

/**
 *
 * 499
 *
 * There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up (u), down (d),
 * left (l) or right (r), but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.
 * There is also a hole in this maze. The ball will drop into the hole if it rolls on to the hole.
 *
 * Given the ball position, the hole position and the maze, find out how the ball could drop into the hole by moving the
 * shortest distance. The distance is defined by the number of empty spaces traveled by the ball from the
 * start position (excluded) to the hole (included). Output the moving directions by using 'u', 'd', 'l' and 'r'.
 * Since there could be several different shortest ways, you should output the lexicographically smallest way.
 * If the ball cannot reach the hole, output "impossible".
 *
 * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the borders
 * of the maze are all walls. The ball and the hole coordinates are represented by row and column indexes.
 *
 * Example 1
 *
 * Input 1: a maze represented by a 2D array
 * 0 (0) 0  0  0
 * 1  1  0  0  1
 * 0  0  0  0  0
 * 0  1  0  0  1
 * 0  1  0 [0] 0
 *
 * Input 2: ball coordinate (rowBall, colBall) = (4, 3)
 * Input 3: hole coordinate (rowHole, colHole) = (0, 1)
 *
 * Output: "lul"
 * Explanation: There are two shortest ways for the ball to drop into the hole.
 * The first way is left -> up -> left, represented by "lul".
 * The second way is up -> left, represented by 'ul'.
 * Both ways have shortest distance 6, but the first way is lexicographically smaller because 'l' < 'u'. So the output is "lul".
 *
 * Example 2
 * Input 1: a maze represented by a 2D array
 *
 *  0  0  0  0  0
 *  1  1  0  0  1
 *  0  0  0  0  0
 * (0) 1  0  0  1
 *  0  1  0 [0] 0
 *
 * Input 2: ball coordinate (rowBall, colBall) = (4, 3)
 * Input 3: hole coordinate (rowHole, colHole) = (3, 0)
 * Output: "impossible"
 * Explanation: The ball cannot reach the hole.
 *
 * Note:
 * There is only one ball and one hole in the maze.
 * Both the ball and hole exist on an empty space, and they will not be at the same position initially.
 * The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
 * The maze contains at least 2 empty spaces, and the width and the height of the maze won't exceed 30.
 *
 */
public class TheMazeIII {

class State {
    int row;
    int col;
    int dist;
    String path;

    public State(int row, int col, int dist, String path) {
        this.row = row;
        this.col = col;
        this.dist = dist;
        this.path = path;
    }
}

/**
Dijkstra's

Intuition
This problem extends Maze II by adding a hole the ball can fall into and requiring the lexicographically smallest path among all shortest paths. The ball must stop at the hole if it rolls over it during movement.

We use Dijkstra's algorithm with a priority queue that orders states by distance first, then by path string lexicographically. This ensures when we first reach the hole, we have both the shortest distance and the lexicographically smallest path for that distance.

Algorithm
- Define a helper function to check if a cell is valid (within bounds and not a wall).
- Define a function to get neighbors: for each direction (ordered as 'd', 'l', 'r', 'u' for lexicographic preference), roll the ball until hitting a wall or the hole.
- Initialize a min-heap with the starting position, ordered by (distance, path).
- Use a set to track visited positions.
- While the heap is not empty:
  Pop the state with minimum distance (and lexicographically smallest path for ties).
  If already visited, skip. If at the hole, return the path.
  Mark as visited and add all neighbor states to the heap.
  If the heap empties without reaching the hole, return "impossible". 

 Time complexity: O(n * logn)
 Space complexity: O(n)
 Where n is the number of squares in maze.
*/
class Solution {
    int[][] directions = new int[][]{{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    String[] textDirections = new String[]{"l", "u", "r", "d"};
    int m;
    int n;

    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        m = maze.length;
        n = maze[0].length;

        PriorityQueue<State> heap = new PriorityQueue<>((a, b) -> {
            int distA = a.dist;
            int distB = b.dist;

            if (distA == distB) {
                return a.path.compareTo(b.path);
            }

            return distA - distB;
        });

        boolean[][] seen = new boolean[m][n];
        heap.add(new State(ball[0], ball[1], 0, ""));

        while (!heap.isEmpty()) {
            State curr = heap.remove();
            int row = curr.row;
            int col = curr.col;

            if (seen[row][col]) {
                continue;
            }

            if (row == hole[0] && col == hole[1]) {
                return curr.path;
            }

            seen[row][col] = true;

            for (State nextState: getNeighbors(row, col, maze, hole)) {
                int nextRow = nextState.row;
                int nextCol = nextState.col;
                int nextDist = nextState.dist;
                String nextChar = nextState.path;
                heap.add(new State(nextRow, nextCol, curr.dist + nextDist, curr.path + nextChar));
            }
        }

        return "impossible";
    }

    private boolean valid(int row, int col, int[][] maze) {
        if (row < 0 || row >= m || col < 0 || col >= n) {
            return false;
        }

        return maze[row][col] == 0;
    }

    private List<State> getNeighbors(int row, int col, int[][] maze, int[] hole) {
        List<State> neighbors = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int dy = directions[i][0];
            int dx = directions[i][1];
            String direction = textDirections[i];

            int currRow = row;
            int currCol = col;
            int dist = 0;

            while (valid(currRow + dy, currCol + dx, maze)) {
                currRow += dy;
                currCol += dx;
                dist++;
                if (currRow == hole[0] && currCol == hole[1]) {
                    break;
                }
            }

            neighbors.add(new State(currRow, currCol, dist, direction));
        }

        return neighbors;
    }
}
