import java.util.*;


/**
 * <h1>LinkedList</h1>
 * This is Linked List class made for DS Homework "Matching".
 * Implemented with generic functionality.
 * Doubly Linked List.
 * Used in AVLTree
 * 
 * @author : integraldx
 * @since : 2019-05-17
 */
public class LinkedList<T> implements Iterable<T>
{
    private Node<T> dummyStart;
    private Node<T> dummyEnd;
    private int count = 0;

    /**
     * Initializes new LinkedList
     */
    public LinkedList()
    {
        dummyStart = new Node<T>(null);
        dummyEnd = new Node<T>(null);
        
        dummyStart.SetRightChild(dummyEnd);
        dummyEnd.SetLeftChild(dummyStart);
    }
    
    /**
     * Adds new item at the end of the list
     * 
     * @param newItem : item to be appended
     */
    public void Add(T newItem)
    {
        Node<T> newNode = new Node<T>(newItem, dummyEnd.GetLeftChild(), dummyEnd);
        dummyEnd.GetLeftChild().SetRightChild(newNode);
        dummyEnd.SetLeftChild(newNode);

        count += 1;
    }

    /**
     * Inserts new item on given index.
     * Throws exception when index is out of bounds.
     * 
     * @param newItem : item to be inserted
     * @param index : position to be inserted at
     */
    public void InsertAt(T newItem, int index)
    {
        if(index >= count || index < 0)
        {
            throw new IndexOutOfBoundsException();
        }

        Node<T> positionNode = NodeAt(index);
        Node<T> newNode = new Node<T>(newItem, positionNode.GetLeftChild(), positionNode);
        positionNode.GetLeftChild().SetRightChild(newNode);
        positionNode.SetLeftChild(newNode);

        count += 1;
    }

    /**
     * Deletes item on given index.
     * Throws exception when index is out of bounds.
     * 
     * @param index : position of the item to be deleted.
     */
    public void DeleteAt(int index)
    {
        if(index >= count || index < 0)
        {
            throw new IndexOutOfBoundsException();
        }
        Node<T> toDelete = NodeAt(index);

        // Make connection between left and right
        toDelete.GetLeftChild().SetRightChild(toDelete.GetRightChild());
        toDelete.GetRightChild().SetLeftChild(toDelete.GetLeftChild());

        count -= 1;
    }

    private Node<T> NodeAt(int index)
    {
        if(index >= count || index < 0)
        {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = dummyStart;
        for(int i = 0; i <= index; i++)
        {
            current = current.GetRightChild();
        }

        return current;
    }

    /**
     * Gets item on given index
     * Throws exception when index is out of bounds.
     * 
     * @param index : Position of the item to get.
     * @return : Intended item to get
     */
    public T At(int index)
    {
        if(index >= count || index < 0)
        {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = dummyStart;
        for(int i = 0; i <= index; i++)
        {
            current = current.GetRightChild();
        }

        return current.GetContent();
    }

    /**
     * Gets total count of the list.
     * @return : count of containing items.
     */
    public int Count()
    {
        return count;
    }

    public Iterator<T> iterator()
    {
        return new MyIterator(this);
    }

    /**
     * Simple Iterator..
     * @param <T>
     */
    public class MyIterator implements Iterator<T>
    {
        LinkedList<T> list;
        Node<T> currentNode;

        public MyIterator(LinkedList<T> initialList)
        {
            list = initialList;
            currentNode = initialList.dummyStart;
        }

        @Override
        public boolean hasNext()
        {
            if (currentNode.GetRightChild() == list.dummyEnd)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        @Override
        public T next()
        {
            if(!hasNext())
            {
                throw new NoSuchElementException();
            }
            currentNode = currentNode.GetRightChild();

            return currentNode.GetContent();
        }
    }
}
