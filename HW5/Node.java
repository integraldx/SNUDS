/**
 * <h1>Node</h1>
 * This is Node class made for DS Homework "Matching".
 * Implemented with generic functionality
 * Used in LinkedList, AVLTree
 * 
 * @author : integraldx
 * @since : 2019-05-17
 */

public class Node<T>
{
    private T content = null; // Immutable
    private Node<T> leftNode = null;
    private Node<T> rightNode = null;

    public Node(T initialContent)
    {
        content = initialContent;
    }

    public Node(T initialContent, Node<T> initialLeftChild, Node<T> initialRightChild)
    {
        this(initialContent);
        leftNode = initialLeftChild;
        rightNode = initialRightChild;
    }

    public T GetContent()
    {
        return content;
    }

    public void SetLeftChild(Node<T> newLeft)
    {
        leftNode = newLeft;
    }

    public void SetRightChild(Node<T> newRight)
    {
        rightNode = newRight;
    }

    public Node<T> GetLeftChild()
    {
        return leftNode;
    }

    public Node<T> GetRightChild()
    {
        return rightNode;
    }

    @Override
    public boolean equals(Object compareWith)
    {
        if(compareWith instanceof Node<?>)
        {
            return content.equals(((Node<?>)compareWith).GetContent());
        }
        else
        {
            return false;
        }
    }
}