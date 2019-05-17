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
            System.out.println("Linked List test : PASSED");
        }
        else 
        {
            System.out.println("Linked List test : FAILED");
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
            AVLTree<String>.AVLNode<String> node = tree.new AVLNode<String>("Hello");
            if (!(node.GetLeftHeight() == 0 && node.GetRightHeight() == 0 && node.GetContent().equals("Hello")))
            {
                return false;
            }

            node.InsertRecursive("Alpha");

            if (!(node.GetLeftHeight() == 1 && node.GetRightHeight() == 0 && node.GetLeftChild().GetContent().equals("Alpha")))
            {
                return false;
            }

            node.InsertRecursive("AA");
            
            if (!(
                node.GetLeftHeight() == 2 && 
                node.GetRightHeight() == 0 && 
                node.GetLeftChild().GetLeftHeight() == 1 &&
                node.GetLeftChild().GetLeftChild().GetContent().equals("AA")
                ))
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
}