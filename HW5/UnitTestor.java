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
    }

    static boolean LinkedListTest()
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

        return true;
    }
}