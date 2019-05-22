import java.util.function.Function;
import java.util.*;
/**
 * <h1>StringMatcher</h1>
 * This is StringMatcher class made for DS Homework "Matching".
 * 
 * @author : integraldx
 * @since : 2019-05-20
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
            return result;
        }
    };

    public StringMatcher()
    {
        table = new HashTable<String, AVLTree<StringSegment>>(hashFunction);
    }

    public void SetOriginString(String newOrigin)
    {
        table = new HashTable<String, AVLTree<StringSegment>>(hashFunction);
        origin = newOrigin;
        SetSubstrings(newOrigin);
    }

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
                    table.Add(substr, tree);
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
                for (int j = 0; j < result.size(); j += 1)
                {
                    for (int k = 0; k < interResult.size(); k += 1)
                    {
                        if(result.get(j).y + pattern.length() - 6 == interResult.get(k).y && result.get(j).x.equals(interResult.get(k).x))
                        {
                            newRes.add(result.get(j));
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


                for (int j = 0; j < result.size(); j += 1)
                {
                    for (int k = 0; k < interResult.size(); k += 1)
                    {
                        if(result.get(j).y + i == interResult.get(k).y && result.get(j).x.equals(interResult.get(k).x))
                        {
                            newRes.add(result.get(j));
                        }
                    }
                }
                result = newRes;
            }
        }
        return result;
    }

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