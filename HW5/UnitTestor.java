import java.util.Random;
import java.util.function.*;

/**
 * <h1>UnitTestor</h1>
 * This is program to single units used in DS Homework "Matching"
 */
public class UnitTestor
{
    public static void main(String[] args)
    {
        if (LinkedListTest())
        {
            System.out.println("LinkedList test : PASSED");
        }
        else 
        {
            System.out.println("LinkedList test : FAILED");
        }

        if (AVLNodeTest())
        {
            System.out.println("AVLNode test : PASSED");
        }
        else 
        {
            System.out.println("AVLNode test : FAILED");
        }

        if (AVLTreeTest())
        {
            System.out.println("AVLTree test : PASSED");
        }
        else
        {
            System.out.println("AVLTree test : FAILED");
        }

        if (HashTableTest())
        {
            System.out.println("HashTable test : PASSED");
        }
        else
        {
            System.out.println("HashTable test : FAILED");
        }

        if (StringMatcherTest())
        {
            System.out.println("StringMatcher test : PASSED");
        }
        else
        {
            System.out.println("StringMatcher test : FAILED");
        }
    }

    static boolean LinkedListTest()
    {
        try
        {
            LinkedList<String> ll = new LinkedList<String>();

            ll.Add("Hello!");
            ll.Add("World!");

            if (!(ll.Count() == 2 && ll.At(0).equals("Hello!") && ll.At(1).equals("World!")))
            {
                return false;
            }

            ll.DeleteAt(0);

            if(!(ll.Count() == 1 && ll.At(0).equals("World!")))
            {
                return false;
            }

            ll.InsertAt("PepsiMan", 0);

            if (!(ll.Count() == 2 && ll.At(0).equals("PepsiMan") && ll.At(1).equals("World!")))
            {
                return false;
            }

            String result = "";
            for(String s : ll)
            {
                result += s;
            }

            if (!result.equals("PepsiManWorld!"))
            {
                return false;
            }
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
            return false;
        }

        return true;
    }

    static boolean AVLNodeTest()
    {
        try
        {
            AVLTree<String> tree = new AVLTree<String>();
            AVLTree<String>.AVLNode node = tree.new AVLNode("");

            Random rand = new Random();

            LinkedList<String> ll = new LinkedList<String>();

            for (int i = 0; i < 10000; i++)
            {
                ll.Add(rand.nextInt() + "");
                node.InsertRecursive(ll.At(i));

                if (!node.GetRightChild().CheckCorrect())
                {
                    return false;
                }
            }

            System.out.println("Insertion passed");

            for (int i = 0; i < 10000; i++)
            {
                int rdNum = rand.nextInt(ll.Count());
                var searchResult = node.SearchRecursive(ll.At(rdNum));

                if ((ll.At(rdNum) != searchResult))
                {
                    System.err.println("Searching \"" + ll.At(rdNum) + "\" failed");
                    System.err.println("Result was \"" + searchResult + "\".");
                    System.err.println(ll.At(rdNum).equals(searchResult));
                    return false;
                }
            }

            if (null != node.SearchRecursive("asdfasdf"))
            {
                return false;
            }

            System.out.println("Search passed");

            for (int i = 0; i < 10000; i++)
            {
                int rdNum = rand.nextInt(ll.Count());
                node.DeleteRecursive(ll.At(rdNum));
                ll.DeleteAt(rdNum);

                if (!node.GetRightChild().CheckCorrect())
                {
                    node.Print(0);
                    return false;
                }
            }

            System.out.println("Deletion passed");
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
            return false;
        }
        
        return true;
    }

    static boolean AVLTreeTest()
    {
        try
        {
            AVLTree<String> tree = new AVLTree<String>();

            Random rand = new Random();

            LinkedList<String> ll = new LinkedList<String>();

            for (int i = 0; i < 10000; i++)
            {
                ll.Add(rand.nextInt() + "");
                tree.Insert(ll.At(i));
            }

            System.out.println("Insertion passed");

            for (int i = 0; i < 10000; i++)
            {
                int rdNum = rand.nextInt(ll.Count());
                var searchResult = tree.Search(ll.At(rdNum));

                if ((ll.At(rdNum) != searchResult))
                {
                    System.err.println("Searching \"" + ll.At(rdNum) + "\" failed");
                    System.err.println("Result was \"" + searchResult + "\".");
                    System.err.println(ll.At(rdNum).equals(searchResult));
                    return false;
                }
            }

            if (null != tree.Search("asdfasdf"))
            {
                return false;
            }

            System.out.println("Search passed");

            for (int i = 0; i < 10000; i++)
            {
                int rdNum = rand.nextInt(ll.Count());
                tree.Delete(ll.At(rdNum));
                ll.DeleteAt(rdNum);
            }

            System.out.println("Deletion passed");
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
            return false;
        }
        
        return true;
    }

    static Function<Integer, Integer> f = new Function<Integer, Integer>(){
        public Integer apply(Integer i)
        {
            return i;
        }
    };

    static boolean HashTableTest()
    {
        try
        {
            HashTable<Integer, String> ht = new HashTable<Integer, String>(f);

            var testStr = "ABCDEFG";
            ht.Add(2, testStr);
            if(ht.Search(102) != testStr)
            {
                return false;
            }
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
            return false;
        }
        return true;
    }

    static boolean StringMatcherTest()
    {
        try
        {
            StringMatcher sm = new StringMatcher();
            sm.SetOriginString(
                "this is a boy. hello, boy.\n" + 
                "it is more important to avoid using a bad data structure.\n" +
                "i am a boyboy. boys be ambitious!\n" + 
                "boyboyoboyboyboy\n" + 
                "more important to avoid it is more important to data"
                );

            var v = sm.SearchPattern("important t");

            for (var i : v)
            {
                System.err.println(i.x + ", " + i.y);
            }

            for (var i : sm.SearchByHash(73))
            {
                System.err.println(i);
            }
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
            System.err.println(e.getStackTrace());
            return false;
        }
        return true;
    }
}