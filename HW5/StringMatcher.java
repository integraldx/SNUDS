import java.util.function.Function;
import java.util.*;
/**
 * <h1>StringMatcher</h1>
 * This is StringMatcher class made for DS Homework "Matching".
 * 
 * @author : integraldx
 * @since : 2019-05-20
 */

/**
 * Simple tuple class
 * @param <X>
 * @param <Y>
 */
class Tuple<X, Y>
{
    public final X x;
    public final Y y;

    public Tuple(X x, Y y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Tuple<X, Y> t)
    {
        return x.equals(t.x) && y.equals(t.y);
    }
}


public class StringMatcher
{
    /**
     * Comparable class used to insert in AVL Tree.
     */
    class StringSegment implements Comparable<StringSegment>
    {
        public String str;
        public LinkedList<Tuple<Integer, Integer>> list;

        public StringSegment()
        {

        }

        public StringSegment(String initialString)
        {
            str = initialString;
        }

        public StringSegment(String initialString, LinkedList<Tuple<Integer, Integer>> ll)
        {
            str = initialString;
            list = ll;
        }

        public int compareTo(StringSegment s)
        {
            return str.compareTo(s.str);
        }
    }

    String origin;
    HashTable<String, AVLTree<StringSegment>> table;
    Function<String, Integer> hashFunction = new Function<String, Integer>()
    {
        public Integer apply(String s)
        {
            int result = 0;
            for (char si : s.toCharArray())
            {
                result += si;
            }
            return result % 100;
        }
    };

    /**
     * Makes new StringMatcher.
     */
    public StringMatcher()
    {

    }

    /**
     * Set original string and convert into internal structure.
     * @param newOrigin : new original string
     */
    public void SetOriginString(String newOrigin)
    {
        table = new HashTable<String, AVLTree<StringSegment>>(hashFunction, 100);
        origin = newOrigin;
        SetSubstrings(newOrigin);
    }

    /**
     * Insert string segments into trees
     * @param newOrigin
     */
    void SetSubstrings(String newOrigin)
    {
        var list = newOrigin.split("\n");
        for (int lineIndex = 0; lineIndex < list.length; lineIndex += 1)
        {
            String line = list[lineIndex];
            for (int i = 0; i < line.length() - 5; i++)
            {
                String substr = line.substring(i, i + 6);
                var tree = table.Search(substr);
                if (tree == null)
                {
                    tree = new AVLTree<StringSegment>();
                    table.Insert(substr, tree);
                }

                var searchSegment = new StringSegment(substr, null);
                var resultSegment = tree.Search(searchSegment);
                if (resultSegment == null)
                {
                    resultSegment = new StringSegment(substr, new LinkedList<Tuple<Integer, Integer>>());
                    tree.Insert(resultSegment);
                }
                resultSegment.list.Add(new Tuple<Integer, Integer>(lineIndex, i));
            }
        }
    }

    /**
     * Search given pattern from string
     * @param pattern 
     * @return : Pattern positions
     */
    public ArrayList<Tuple<Integer, Integer>> SearchPattern(String pattern)
    {
        ArrayList<Tuple<Integer, Integer>> result = SearchFixedPattern(pattern.substring(0, 6));

        for (int i = 6; i < pattern.length(); i += 6)
        {
            String substr;
            ArrayList<Tuple<Integer, Integer>> interResult;
            if (i > pattern.length() - 6)
            {
                substr = pattern.substring(pattern.length() - 6);
                interResult = SearchFixedPattern(substr);

                var newRes = new ArrayList<Tuple<Integer, Integer>>();
                int indexPointer = 0;
                for (int j = 0; j < result.size(); j += 1)
                {
                    while (true)
                    {
                        if (indexPointer >= interResult.size())
                        {
                            break;
                        }

                        if (interResult.get(indexPointer).x < result.get(j).x)
                        {
                            indexPointer++;
                            continue;
                        }
                        else if (interResult.get(indexPointer).x.equals(result.get(j).x))
                        {
                            if (result.get(j).y + pattern.length() - 6 == interResult.get(indexPointer).y)
                            {
                                newRes.add(result.get(j));
                                break;
                            }
                            else if (interResult.get(indexPointer).y < result.get(j).y + pattern.length() - 6)
                            {
                                indexPointer++;
                                continue;
                            }
                            else
                            {
                                break;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                result = newRes;
            }
            else
            {
                substr = pattern.substring(i, i + 6);
                interResult = SearchFixedPattern(substr);

                var newRes = new ArrayList<Tuple<Integer, Integer>>();
                int indexPointer = 0;

                for (int j = 0; j < result.size(); j += 1)
                {
                    while (true)
                    {
                        if (indexPointer >= interResult.size())
                        {
                            break;
                        }

                        if (interResult.get(indexPointer).x < result.get(j).x)
                        {
                            indexPointer++;
                            continue;
                        }
                        else if (interResult.get(indexPointer).x.equals(result.get(j).x))
                        {
                            if (result.get(j).y + i == interResult.get(indexPointer).y)
                            {
                                newRes.add(result.get(j));
                                break;
                            }
                            else if (interResult.get(indexPointer).y < result.get(j).y + i)
                            {
                                indexPointer++;
                                continue;
                            }
                            else
                            {
                                break;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                result = newRes;
            }
        }
        return result;
    }

    /**
     * Search pattern with length of 6.
     * Any excess characters are discarded.
     * @param pattern
     * @return
     */
    private ArrayList<Tuple<Integer, Integer>> SearchFixedPattern(String pattern)
    {
        ArrayList<Tuple<Integer, Integer>> result = new ArrayList<Tuple<Integer, Integer>>();

        var tree = table.Search(pattern.substring(0, 6));
        if (tree == null)
        {
            return result;
        }

        var node = tree.Search(new StringSegment(pattern.substring(0, 6)));
        if (node == null)
        {
            return result;
        }

        for (var i : node.list)
        {
            result.add(new Tuple<Integer, Integer>(i.x + 1, i.y + 1));
        }

        return result;
    }

    /**
     * Search strings match to given hash value.
     * @param hashValue
     * @return
     */
    public ArrayList<String> SearchByHash(int hashValue)
    {
        ArrayList<String> result = new ArrayList<String>();
        if (table.SearchByHash(hashValue) == null)
        {
            return result;
        }
        var trav = table.SearchByHash(hashValue).preorderTraversal();

        for (var i : trav)
        {
            result.add(i.str);
        }

        return result;
    }
}