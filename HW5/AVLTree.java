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
    public class AVLNode extends Node<T>
    {
        protected int leftHeight = 0;
        protected int rightHeight = 0;

        public AVLNode(T initialContent)
        {
            super(initialContent);
        }

        public AVLNode(T initialContent, AVLNode initialLeft, AVLNode initialRight)
        {
            super(initialContent, initialLeft, initialRight);
        }

        private void SetContent(T newContent)
        {
            content = newContent;
        }

        public AVLNode GetLeftChild()
        {
            return (AVLNode)leftNode;
        }

        public AVLNode GetRightChild()
        {
            return (AVLNode)rightNode;
        }

        public void SetLeftChild(AVLNode newLeft)
        {
            leftNode = newLeft;
        }

        public void SetRightChild(AVLNode newRight)
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
            int compareResult = GetContent().compareTo(newItem);
            boolean insertResult = false;
            int totalHeight = leftHeight > rightHeight ? leftHeight : rightHeight;
            if (compareResult > 0)
            {
                if (GetLeftChild() != null)
                {
                    insertResult = GetLeftChild().InsertRecursive(newItem);
                }
                else
                {
                    SetLeftChild(new AVLNode(newItem));
                    insertResult = true;
                }

                if (insertResult)
                {
                    leftHeight += 1;
                }
            }
            else if (compareResult < 0)
            {
                if (GetRightChild() != null)
                {
                    insertResult = GetRightChild().InsertRecursive(newItem);
                }
                else
                {
                    SetRightChild(new AVLNode(newItem));
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

            if(totalHeight == (leftHeight > rightHeight ? leftHeight : rightHeight))
            {
                insertResult = false;
            }

            return insertResult;
        }

        public boolean DeleteRecursive(T searchKey)
        {
            int compareResult = GetContent().compareTo(searchKey);
            boolean deleteResult = false;
            int totalHeight = leftHeight > rightHeight ? leftHeight : rightHeight;
            if (compareResult > 0)
            {
                if (GetLeftChild() != null)
                {
                    if (GetLeftChild().GetContent().equals(searchKey))
                    {
                        AVLNode toDeleteNode = GetLeftChild();
                        AVLNode replaceNode = toDeleteNode.GetClosestNode();
                        if(replaceNode != null)
                        {
                            deleteResult = toDeleteNode.DeleteRecursive(replaceNode.GetContent());
                            toDeleteNode.SetContent(replaceNode.GetContent());
                        }
                        else
                        {
                            SetLeftChild(null);
                            deleteResult = true;
                        }
                    }
                    else
                    {
                        deleteResult = GetLeftChild().DeleteRecursive(searchKey);
                    }
                }

                if (deleteResult)
                {
                    leftHeight -= 1;
                }
            }
            else if (compareResult < 0)
            {
                if (GetRightChild() != null)
                {
                    if (GetRightChild().GetContent().equals(searchKey))
                    {
                        AVLNode toDeleteNode = GetRightChild();
                        AVLNode replaceNode = toDeleteNode.GetClosestNode();
                        if(replaceNode != null)
                        {
                            deleteResult = toDeleteNode.DeleteRecursive(replaceNode.GetContent());
                            toDeleteNode.SetContent(replaceNode.GetContent());
                        }
                        else
                        {
                            SetRightChild(null);
                            deleteResult = true;
                        }
                    }
                    else
                    {
                        deleteResult = GetRightChild().DeleteRecursive(searchKey);
                    }
                }

                if (deleteResult)
                {
                    rightHeight -= 1;
                }
            }
            else
            {
                deleteResult = true;
            }

            if(totalHeight == (leftHeight > rightHeight ? leftHeight : rightHeight))
            {
                deleteResult = false;
            }

            return deleteResult;
        }

        AVLNode GetClosestNode()
        {
            AVLNode result = null;
            if (GetLeftChild() != null)
            {
                result = GetLeftChild();

                while(result.GetRightChild() != null)
                {
                    result = result.GetRightChild();
                }
            }
            else if (GetRightChild() != null)
            {
                result = GetRightChild();

                while(result.GetLeftChild() != null)
                {
                    result = result.GetLeftChild();
                }
            }

            return result;
        }


        public T SearchRecursive(T searchKey)
        {
            throw new UnsupportedOperationException();
        }
    }

    private AVLNode dummyRootNode;

    /**
     * Initializes new AVLTree
     */
    public AVLTree()
    {
        dummyRootNode = null;
    }

    public void Insert(T newItem)
    {
        
    }
}