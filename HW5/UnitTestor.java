
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
            AVLTree<String>.AVLNode node = tree.new AVLNode("Hello");
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

            node.InsertRecursive("Ao");

            if (!(
                node.GetLeftHeight() == 2 && 
                node.GetRightHeight() == 0 && 
                node.GetLeftChild().GetLeftHeight() == 1 &&
                node.GetLeftChild().GetRightHeight() == 1 &&
                node.GetLeftChild().GetRightChild().GetContent().equals("Ao")
                ))
            {
                return false;
            }

            node.InsertRecursive("AB");

            if (!(
                node.GetLeftHeight() == 3 && 
                node.GetRightHeight() == 0 && 
                node.GetLeftChild().GetLeftHeight() == 2 &&
                node.GetLeftChild().GetRightHeight() == 1 &&
                node.GetLeftChild().GetLeftChild().GetRightHeight() == 1 &&
                node.GetLeftChild().GetLeftChild().GetRightChild().GetContent().equals("AB")
                ))
            {
                return false;
            }

            node.DeleteRecursive("Alpha");

            if (!(
                node.GetLeftHeight() == 2 && 
                node.GetRightHeight() == 0 && 
                node.GetLeftChild().GetLeftHeight() == 1 &&
                node.GetLeftChild().GetRightHeight() == 1 &&
                node.GetLeftChild().GetLeftChild().GetRightHeight() == 0 &&
                node.GetLeftChild().GetContent().equals("AB") &&
                node.GetLeftChild().GetLeftChild().GetRightChild() == null
                ))
            {
                System.err.println( "Deletion Test Failed" + "\n" + 
                    node.GetLeftHeight() + "\n" + 
                    node.GetRightHeight() + "\n" + 
                    node.GetLeftChild().GetLeftHeight() + "\n" + 
                    node.GetLeftChild().GetRightHeight() + "\n" + 
                    node.GetLeftChild().GetLeftChild().GetRightHeight() +  "\n" + 
                    node.GetLeftChild().GetContent() + "\n" + 
                    (node.GetLeftChild().GetLeftChild().GetRightChild() == null ? "True" : "False")
                );
                return false;
            }

            node.DeleteRecursive("AA");

            if (!(
                node.GetLeftHeight() == 2 && 
                node.GetRightHeight() == 0 && 
                node.GetLeftChild().GetLeftHeight() == 0 &&
                node.GetLeftChild().GetRightHeight() == 1 &&
                node.GetLeftChild().GetLeftChild() == null &&
                node.GetLeftChild().GetRightChild().GetContent().equals("Ao")
                ))
            {
                System.err.println( "Deletion Test Failed" + "\n" + 
                    (node.GetLeftHeight() == 2  ? "True" : "False" )  + "\n" + 
                    (node.GetRightHeight() == 0  ? "True" : "False" )  + "\n" + 
                    (node.GetLeftChild().GetLeftHeight() == 0 ? "True" : "False" )  + "\n" + 
                    (node.GetLeftChild().GetRightHeight() == 1 ? "True" : "False" ) + "\n" + 
                    (node.GetLeftChild().GetLeftChild() == null ? "True" : "False" ) + "\n" + 
                    (node.GetLeftChild().GetRightChild().GetContent().equals("Ao") ? "True" : "False")
                );
                return false;
            }
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