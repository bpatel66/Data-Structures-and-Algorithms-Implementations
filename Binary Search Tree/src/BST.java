import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a binary search tree.
 *
 * @author Balkrishna Patel
 * @userid bpatel66
 * @GTID 903023192
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that has been passed"
                    + " in is null, please pass in new data.");
        } else {
            for (T node : data) {
                add(node);
            }
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was pased in is"
                    + " null, please pass in new data.");
        } else {
            root = rAdd(data, root);
        }
    }

    /**
     * Recursive helper for the add method where all traversals cases are
     * addressed.
     *  Case 0: the node is null
     *  Case 1: the node's data is equal to the data that was passed
     *  Case 2: the node's data is less than the data that was passed in
     *  Case 3: the node's data is greater than the data that was passed in
     *
     * @param data the data that we are trying to add into the BST
     * @param node the node that we are testing against to determine if/
     *             where we need to traverse
     * @return the changed or unchanged node.
     */
    @SuppressWarnings("unchecked")
    private BSTNode<T> rAdd(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode(data);
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(rAdd(data, node.getLeft()));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(rAdd(data, node.getRight()));
        } else {
            return node;
        }
        return node;
        /*if (node == null) {
            size++;
            return new BSTNode(data);
        }
        if (node.getData().compareTo(data) == 0) {
            return node;
        }
        if (node.getData().compareTo(data) < 0) {
            node.setRight(rAdd(data, node.getRight()));
        }
        if (node.getData().compareTo(data) > 0) {
            node.setLeft(rAdd(data, node.getLeft()));
        }
        return node;*/
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null, please pass in new data.");
        }
        if (root == null) {
            throw new NoSuchElementException("The root is null, the tree is "
                    + "empty.");
        }
        BSTNode<T> dummyNode = new BSTNode<>(null);
        root = rRemove(data, root, dummyNode);
        return dummyNode.getData();
    }

    /**
     * The recursive helper for the remove method that addresses all the
     * cases for remove.
     *  Case 0: the node that we are at is null
     *  Case 1: the data is less than the node's data
     *  Case 2: the data is greater than the node's data
     *  Case 3: the data is equal to the node's data so we address the 3
     *          cases for removal
     *      Case 3.0: No Children
     *      Case 3.1.0: Left Child Only
     *      Case 3.1.1: Right Child Only
     *      Case 3.2: Two Children find Successor
     *
     * @throws NoSuchElementException the node we are at is null, so the data
     *         does not exist.
     * @param data we are trying to remove
     * @param node we are currently at
     * @param dummyNode where we assign the data of the node we are removing
     * @return changed or unchanged node.
     */
    private BSTNode<T> rRemove(T data, BSTNode<T> node, BSTNode<T> dummyNode) {
        if (node == null) {
            // Case 0: the node that was passed in is null, meaning the data
            // does not exist.
            throw new NoSuchElementException("The data does not exist in the "
                    + "BST.");
        }
        if (data.compareTo(node.getData()) < 0) {
            // Case 1: the data is "less" than the current node's data so you
            // need to traverse left.
            node.setLeft(rRemove(data, node.getLeft(), dummyNode));
        }
        if (data.compareTo(node.getData()) > 0) {
            // Case 2: the data is "greater" than the current node's data so
            // you need to traverse right.
            node.setRight(rRemove(data, node.getRight(), dummyNode));
        }
        if (data.equals(node.getData())) {
            // Case 3: the data is "equal" to the node's data now you can
            // apply one of the removal cases to remove the node.
            dummyNode.setData(node.getData());
            if (node.getLeft() == null && node.getRight() == null) {
                // Case 3.0: node has no children
                size--;
                return null;
            }
            if (node.getLeft() != null && node.getRight() == null) {
                // Case 3.1.0: node has left child
                size--;
                return node.getLeft();
            }
            if (node.getLeft() == null && node.getRight() != null) {
                //  Case 3.1.1: node has right child
                size--;
                return node.getRight();
            }
            if (node.getLeft() != null && node.getRight() != null) {
                // Case 3.2: node has 2 children, find successor
                BSTNode<T> tempNode = new BSTNode<>(null);
                node.setRight(rSuccessor(node.getRight(), tempNode));
                node.setData(tempNode.getData());
                size--;
            }
        }
        return node;
    }

    /**
     * The recursive helper method to find the successor
     *
     * @param node the node that we are at
     * @param tempNode the node were the successor will be stored
     * @return a changed or unchanged node
     */
    private BSTNode<T> rSuccessor(BSTNode<T> node, BSTNode<T> tempNode) {
        if (node.getLeft() == null) {
            //  successor has been found
            tempNode.setData(node.getData());
            return node.getRight();
        } else {
            // successor has not been found
            node.setLeft(rSuccessor(node.getLeft(), tempNode));
            return node;
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null, please pass in new data.");
        } else {
            return rGet(data, root);
        }
    }

    /**
     * The recursive helper method for the get method.
     *
     * @throws NoSuchElementException if the data is not found in the BST.
     * @param data The data that is being searched for.
     * @param node The node that is being compared against the data.
     * @return the data if it is found.
     */
    private T rGet(T data, BSTNode<T> node) {
        if (node != null && node.getData().compareTo(data) == 0) {
            return node.getData();
        }
        if (node != null && node.getData().compareTo(data) < 0) {
            return rGet(data, node.getRight());
        }
        if (node != null && node.getData().compareTo(data) > 0) {
            return rGet(data, node.getLeft());
        }
        throw new java.util.NoSuchElementException("The data was not "
                + "found in the tree");
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null, please pass in new data.");
        } else {
            return rContains(data, root);
        }
    }

    /**
     * The recursive helper method for the contains method.
     *
     * @param data The data that is being searched for.
     * @param node The node that the data will is currently being tested
     *             against.
     * @return true if the the data is found in the BST, false otherwise.
     */
    private boolean rContains(T data, BSTNode<T> node) {
        if (node != null && node.getData().compareTo(data) == 0) {
            return true;
        }
        if (node != null && node.getData().compareTo(data) < 0) {
            return rContains(data, node.getRight());
        }
        if (node != null && node.getData().compareTo(data) > 0) {
            return rContains(data, node.getLeft());
        }
        return false;
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorderedList = new ArrayList<T>();
        rPreorder(root, preorderedList);
        return preorderedList;
    }

    /**
     * The recursive helper method compiles a pre-ordered list of the BST.
     *
     * @param node The node where the helper method is being called from.
     * @return A pre-ordered list of the BST.
     */
    private void rPreorder(BSTNode<T> node, List<T> preOrderedList) {
        if (node == null) {
            return;
        } else {
            preOrderedList.add(node.getData());
            rPreorder(node.getLeft(), preOrderedList);
            rPreorder(node.getRight(), preOrderedList);
        }
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorderedList = new ArrayList<>();
        rInorder(root, inorderedList);
        return inorderedList;
    }

    /**
     * The recursive helper method that complies an inorder list of the BST
     *
     * @param node The node that the method is being called from
     * @return An inordered list of the BST
     */
    private void rInorder(BSTNode<T> node, List<T> inorderedList) {
        if (node == null) {
            return;
        } else {
            rInorder(node.getLeft(), inorderedList);
            inorderedList.add(node.getData());
            rInorder(node.getRight(), inorderedList);
        }
    }
    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorderedList = new ArrayList<>();
        rPostorder(root, postorderedList);
        return postorderedList;
    }

    /**
     * The recursive helper method that compliles a post-ordered list of the
     * BST.
     *
     * @param node The node that the method is being called from.
     * @return a post-ordered list of the BST.
     */
    private  void rPostorder(BSTNode<T> node, List<T> postOrderedList) {
        List<T> postorderedList = new ArrayList<>();
        if (node == null) {
            return;
        } else {
            rPostorder(node.getLeft(), postOrderedList);
            rPostorder(node.getRight(), postOrderedList);
            postOrderedList.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> levelordeerList = new ArrayList<>();
        if (size == 0) {
            return levelordeerList;
        }
        Queue<BSTNode<T>> tempQueue = new LinkedList<>();
        tempQueue.add(root);
        BSTNode<T> tempNode;
        while (!tempQueue.isEmpty()) {
            tempNode = tempQueue.remove();
            levelordeerList.add(tempNode.getData());
            if (tempNode.getLeft() != null) {
                tempQueue.add(tempNode.getLeft());
            }
            if (tempNode.getRight() != null) {
                tempQueue.add(tempNode.getRight());
            }
        }
        return levelordeerList;
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * The recursive helper method that helps find the Height of a node.
     *
     * @param node The node whose height is being calculated
     * @return the height of the node.
     */
    private int rHeight(BSTNode<T> node) {
        if (node == null) {
            return - 1;
        } else  {
            return 1 + Math.max(rHeight(node.getLeft()), rHeight(node
                    .getRight()));
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
