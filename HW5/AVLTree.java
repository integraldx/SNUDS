import java.lang.UnsupportedOperationException;
/**
 * <h1>AVLTree</h1>
 * This is AVL tree class made for DS Homework "Matching".
 * Implemented with generic functionality
 * Used in HashTable
 * 
 * @author : integraldx
 * @since : 2019-05-17
 */
public class AVLTree<T extends Comparable<T>>
{
    public class AVLNode<T extends Comparable<T>> extends Node<T>
    {
        protected int leftHeight = 0;
        protected int rightHeight = 0;

        public AVLNode(T initialContent)
        {
            super(initialContent);
        }

        public AVLNode(T initialContent, AVLNode<T> initialLeft, AVLNode<T> initialRight)
        {
            super(initialContent, initialLeft, initialRight);
        }

        public AVLNode<T> GetLeftChild()
        {
            return (AVLNode<T>)leftNode;
        }

        public AVLNode<T> GetRightChild()
        {
            return (AVLNode<T>)rightNode;
        }

        public void SetLeftChild(AVLNode<T> newLeft)
        {
            leftNode = newLeft;
        }

        public void SetRightChild(AVLNode<T> newRight)
        {
            rightNode = newRight;
        }

        public int GetLeftHeight()
        {
            return leftHeight;
        }

        public int GetRightHeight()
        {
            return rightHeight;
        }

        public boolean InsertRecursive(T newItem)
        {
            int compareResult = content.compareTo(newItem);
            boolean insertResult = false;
            if (compareResult > 0)
            {
                if (leftNode != null)
                {
                    insertResult = ((AVLNode<T>)leftNode).InsertRecursive(newItem);
                }
                else
                {
                    SetLeftChild(new AVLNode<T>(newItem));
                    insertResult = true;
                }

                if (insertResult)
                {
                    leftHeight += 1;
                }
            }
            else if (content.compareTo(newItem) < 0)
            {
                if (rightNode != null)
                {
                    insertResult = ((AVLNode<T>)rightNode).InsertRecursive(newItem);
                }
                else
                {
                    SetRightChild(new AVLNode<T>(newItem));
                    insertResult = true;
                }

                if (insertResult)
                {
                    rightHeight += 1;
                }
            }
            else
            {
                content = newItem;
            }

            return insertResult;
        }

        public void DeleteRecursive(T searchKey)
        {
            throw new UnsupportedOperationException();
        }

        public T SearchRecursive(T searchKey)
        {
            throw new UnsupportedOperationException();
        }
    }

    private AVLNode<T> rootNode;

    /**
     * Initializes new AVLTree
     */
    public AVLTree()
    {
        rootNode = null;
    }

    public void Insert(T newItem)
    {
        
    }
}