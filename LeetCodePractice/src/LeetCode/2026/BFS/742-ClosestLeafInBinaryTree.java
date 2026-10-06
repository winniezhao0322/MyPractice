/**
 * Given a binary tree where every node has a unique value, and a target key k,
 * find the closest (nearest) leaf node to target k in the tree.
 *
 * A node is called a leaf if it has no children.
 * In the following examples, the input tree is represented in flattened form row by row.
 * The actual root tree given will be a TreeNode object.
 *
 * Example 1:
 * Input: root = [1, 3, 2], k = 1
 * Diagram of binary tree:
 *    1
 *   / \
 *  3   2
 * Output: 2 (or 3)
 *
 * Explanation: Either 2 or 3 is the closest leaf node to 1.
 *
 * Example 2:
 * Input: root = [1], k = 1
 * Output: 1
 *
 * Explanation: The closest leaf node is the root node itself.
 *
 * Example 3:
 *
 * Input: root = [1,2,3,4,null,null,null,5,null,6], k = 2
 * Diagram of binary tree:
 *         1
 *        / \
 *       2   3
 *      /
 *     4
 *    /
 *   5
 *  /
 * 6
 *
 * Output: 3
 * Explanation: The leaf node with value 3 (and not the leaf node with value 6) is closest to the node with value 2.
 *
 * Note:
 * root represents a binary tree with at least 1 node and at most 1000 nodes.
 * Every node has a unique node.val in range [1, 1000].
 * There exists some node in the given binary tree for which node.val == k.

 * Created by WinnieZhao on 1/1/2018.
 */
public class ClosestLeafInBinaryTree {

    /**
     * First, preform DFS on root in order to find the node whose val = k, at the meantime use HashMap
     * to keep record of all back edges from child to parent
     * Then perform BFS on this node to find the closest leaf node.
     *
     * @param root
     * @param k
     * @return
     */
    TreeNode kNode = null;
    Map<TreeNode, TreeNode> edges = new HashMap<>(); // store all edges that trace node back to its parent
  
    public int findClosestLeaf(TreeNode root, int k) {
        if (root == null) {
            return -1;
        }
        this.dfs(root, k);
        if (kNode == null) {
            return -1;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(kNode);

        Set<TreeNode> visited = new HashSet<>(); 
        visited.add(kNode);
        while(!queue.isEmpty()) {
            TreeNode curr = queue.poll();

            if (curr.left == null && curr.right == null) {
                return cur.val;
            }
            // Finding leaves below target node
            if (curr.left != null && visited.add(curr.left)) {
                queue.add(curr.left);
            }
            if (curr.right != null && visited.add(curr.right)) {
                queue.add(curr.right);
            }
            // traverse upwards toward the parent nodes via edges
            if (edges.containsKey(curr) && visited.add(edges.get(curr))) {  // go alone the back edge
                queue.add(edges.get(curr));
            }
        }
         return -1;
    }

    private void dfs(TreeNode node, int k) {
        if (node == null) {
            return;
        }
        if (node.val == k) {
            kNode = node;
        }

        if (node.left ! = null) {
            edges.putIfAbsent(node.left, node);
            dfs(node.left, k);
        }
        if (node.right != null) {
            edges.putIfAbsent(node.right, node);
            dfs(node.right, k);
        }
    }
