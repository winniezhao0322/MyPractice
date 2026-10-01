package LeetCode.Medium;

import java.util.HashMap;
import java.util.Map;

/**
 * 737
 *
 * Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs,
 * determine if two sentences are similar.
 *
 * For example, words1 = ["great", "acting", "skills"] and words2 = ["fine", "drama", "talent"] are similar,
 * if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], ["acting","drama"], ["skills","talent"]].
 *
 * Note that the similarity relation is transitive. For example, if "great" and "good" are similar,
 * and "fine" and "good" are similar, then "great" and "fine" are similar.
 *
 * Similarity is also symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.
 * Also, a word is always similar with itself.
 * For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar,
 * even though there are no specified similar word pairs.
 *
 * Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"]
 * can never be similar to words2 = ["doubleplus","good"].
 *
 * Note:
 * The length of words1 and words2 will not exceed 1000.
 * The length of pairs will not exceed 2000.
 * The length of each pairs[i] will be 2.
 * The length of each words[i] and pairs[i][j] will be in the range [1, 20]

 * Created by WinnieZhao on 12/31/2017.
 */
public class SentenceSimilarityII {

    /**
     * This is a good use case for Union-Find, compare to Sentence Similarity I,
     * here the similarity between words are transitive, so all the connected(similar)
     * words should be group into an union represented by their ultimate parent
     *
     * The connections can be represented by an parent map Map<String, String> m, which record
     * the direct parent-ship we learned in each pair, but not the ultimate-parent.
     * To build it, go through the input pairs, for each pair<w1, w2>, use the recursive
     * find() method to find the ultimate-parent for both word - parent1, parent2,
     * if they are different, assign parent1 as parent of parent2(or the other way around),
     * so that the to families are merged.
     *
     * The classic find(x) method will find the ultimate-parent of x. I modified it a little bit,
     * make it do a little of extra initialization work - assign x itself as its parent when it is
     * not initialize - so that we don't have to explicitly initialize the map at the beginning.

     * @param words1
     * @param words2
     * @param pairs
     * @return
     */

    /**
     * Time Complexity: O(N + P) in general
     - N: 句子数组 words1 / words2 的长度。
     - P：相似词对 pairs 的数量。
     **/
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length) {
            return false;
        }

        Map<String, String> parents = new HashMap<>();

        // 1. Union pairs
        for (String[] pair : pairs) {
            String u = pair[0];
            String v = pair[1];
            
            String rootU = find(u, parents);
            String rootV = find(v, parents);

            if (!rootU.equals(rootV)) {
                parents.put(rootU, rootV); // Link roots together
            }
        }

        // 2. Check if corresponding words share the same root component
        for (int i = 0; i < words1.length; i++) {
            String w1 = words1[i];
            String w2 = words2[i];

            if (w1.equals(w2)) {
                continue;
            }

            if (!find(w1, parents).equals(find(w2, parents))) {
                return false;
            }
        }

        return true;
    }

    private String find(String target, Map<String, String> parents) {
        if (!parents.containsKey(target)) {
            parents.put(target, target);
            return target;
        }
        if (target.equals(parents.get(target))) {
            return target;
        }
          
        String root = find(parents.get(target), parents);
        // Path compression to reduce the paths to reach from target -> root
        parents.put(target, root);
        return root;
    }
}
