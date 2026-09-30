package LeetCode.Medium;

import java.util.HashSet;
import java.util.Set;

/**
 * 694
 *
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land)
 * connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid
 * are surrounded by water.
 *
 * Count the number of distinct islands. An island is considered to be the same as another if and
 * only if one island can be translated (and not rotated or reflected) to equal the other.
 *
 * Example 1:
 * 11000
 * 11000
 * 00011
 * 00011
 * Given the above grid map, return 1.
 *
 * Example 2:
 * 11011
 * 10000
 * 00001
 * 11011
 * Given the above grid map, return 3.
 *
 * Notice that:
 * 11
 * 1
 * and
 * 1
 * 11
 * are considered different island shapes, because we do not consider reflection / rotation.
 * Note: The length of each dimension in the given grid does not exceed 50.
 */
public class NumberOfDistinctIslands {

    /**
     * Hash By Local Coordinates
     *
     * Since two islands are the same if one can be translated to match another,
     * let's translate every island so the top-left corner is (0, 0)
     * For example, if an island is made from squares [(2, 3), (2, 4), (3, 4)],
     * we can think of this shape as [(0, 0), (0, 1), (1, 1)] when anchored at the top-left corner.
     * From there, we only need to check how many unique shapes there ignoring permutations
     * (so [(0, 0), (0, 1)] is the same as [(0, 1), (1, 0)]). We use sets directly as we have showcased below,
     * but we could have also sorted each list and put those sorted lists in our set structure shapes.
     *
     */

    int[][] dirs = new int[][] {{0,1}, {0,-1}, {1,0}, {-1,0}};
    char[] paths = new char[] {'D', 'U', 'R', 'L' };

    // The time complexity of this solution is O(M * N), 
    // where M is the number of rows and N is the number of columns in the grid.
    public int numDistinctIslands(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        Set<String> res = new HashSet<>();
        boolean[][] visited = new boolean[m][n];

        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    StringBuilder sb = new StringBuilder();
                    dfs(grid, visited, i, j, sb);
                    res.add(sb.toString());
                }
            }
        }
        return res.size();
    }

    private void dfs(int[][] grid, boolean[][] visited, int x, int y, StringBuilder sb) {
        int m = grid.length;
        int n = grid[0].length;

        if (x < 0 || y < 0 || x >= m || y >=n || visited[x][y] || grid[x][y] != 1) {
            return;
        }

        visited[x][y] = true;

        for (int i=0; i<dirs.length; i++) {
            int[] dir = dirs[i];
            sb.append(paths[i]);
            dfs(grid, visited, x + dir[0], y + dir[1], sb);
        }
        // 回溯时追加撤销标记 'b' (backtrack)
        sb.append('b'); 
    }
}
