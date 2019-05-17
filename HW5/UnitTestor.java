import java.util.Random;

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

            for (int i = 0; i < 100; i++)
            {
                ll.Add(rand.nextInt() + "");
                node.InsertRecursive(ll.At(ll.Count() - 1));

                if (!node.GetRightChild().CheckCorrect())
                {
                    return false;
                }
            }

            System.out.println("Insertion passed");

            for (int i = 0; i < 100; i++)
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
}