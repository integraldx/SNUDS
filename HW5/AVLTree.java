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

        /**
         * Initializes new AVLNode with no childs
         * @param initialContent : initial value
         */
        public AVLNode(T initialContent)
        {
            super(initialContent);
        }

        /**
         * Initializes new AVLNode with given childs
         * @param initialContent : initial value
         * @param initialLeft : left child
         * @param initialRight : right child
         */
        public AVLNode(T initialContent, AVLNode initialLeft, AVLNode initialRight)
        {
            super(initialContent, initialLeft, initialRight);
        }

        /**
         * Sets new content. Used in deletion.
         * @param newContent 
         */
        private void SetContent(T newContent)
        {
            content = newContent;
        }

        /**
         * Gets left child node
         */
        public AVLNode GetLeftChild()
        {
            return (AVLNode)leftNode;
        }

        /**
         * Gets right child node
         */
        public AVLNode GetRightChild()
        {
            return (AVLNode)rightNode;
        }

        /**
         * Sets left child node.
         * @param newLeft
         */
        private void SetLeftChild(AVLNode newLeft)
        {
            leftNode = newLeft;
        }
        
        /**
         * Sets right child node.
         * @param newRight
         */
        private void SetRightChild(AVLNode newRight)
        {
            rightNode = newRight;
        }

        /**
         * Gets left height value
         * @return left height
         */
        public int GetLeftHeight()
        {
            return leftHeight;
        }

        /**
         * Gets right height value
         * @return right height
         */
        public int GetRightHeight()
        {
            return rightHeight;
        }

        /**
         * Inserts new item recursively
         * @param newItem : New item to be inserted
         * @return : Insertion status (used to determine height change)
         */
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

        /**
         * Deletes given item recursively
         * @param searchKey : item content to delete
         * @return : deletion status (used to determine height change)
         */
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

        /**
         * Gets closest node from this. Used in deletion
         * @return closest node
         */
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

        /**
         * Rotates subtree clockwise and returns new subtree's root
         * @return new Root
         */
        public AVLNode RotateCW()
        {
            AVLNode finalNewNode = GetLeftChild();
            if(finalNewNode == null)
            {
                return this;
            }
            else
            {
                var mid = finalNewNode.GetRightChild();
                finalNewNode.SetRightChild(this);
                this.SetLeftChild(mid);

                this.leftHeight = mid.GetHeight();
                finalNewNode.rightHeight = this.GetHeight();
            }

            return finalNewNode;
        }

        /**
         * Rotates subtree counter-clockwise and returns new subtree's root
         * @return new Root
         */
        public AVLNode RotateCCW()
        {
            AVLNode finalNewNode = GetRightChild();
            if(finalNewNode == null)
            {
                return this;
            }
            else
            {
                var mid = finalNewNode.GetLeftChild();
                finalNewNode.SetLeftChild(this);
                this.SetRightChild(mid);

                this.rightHeight = mid.GetHeight();
                finalNewNode.leftHeight = this.GetHeight();
            }

            return finalNewNode;
        }

        /**
         * returns subtree's height counted from this
         * @return
         */
        public int GetHeight()
        {
            return (GetLeftHeight() > GetRightHeight() ? GetLeftHeight() : GetRightHeight()) + 1;
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