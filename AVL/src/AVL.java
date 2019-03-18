import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Balkirshna Patel
 * @userid bpatel66
 * @GTID 903023192
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null.");
        } else {
            for (T node: data) {
                if (node == null) {
                    throw new IllegalArgumentException("There is null data in"
                            + " the collection that was passed in.");
                } else {
                    add(node);
                }
            }
        }

    }

    /**
     * Add the data as a leaf to the AVL. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed is "
                    + "null");
        } else {
            root = rAdd(data, root);
        }
    }

    /**
     * Recursive helper method for the add method.
     *
     * @param data being added to the AVL
     * @param node whose data is being compared to to determine where to
     *             traverse, if you need to traverse.
     * @return the edited tree.
     */
    private AVLNode<T> rAdd(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            node = new AVLNode<>(data);
        }
        if (node.getData().compareTo(data) < 0) {
            node.setRight(rAdd(data, node.getRight()));
        }
        if (node.getData().compareTo(data) > 0) {
            node.setLeft(rAdd(data, node.getLeft()));
        }
        update(node);
        if (node.getBalanceFactor() < -1) {
            if (node.getRight() != null && node.getRight().
                    getBalanceFactor() > 0) {
                node.setRight(rRightRotate(node.getRight()));
            }
            node = rLeftRotate(node);
        }
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft() != null && node.getLeft().getBalanceFactor()
                    < 0) {
                node.setLeft(rLeftRotate(node.getLeft()));
            }
            node = rRightRotate(node);
        }
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * 1: The data is a leaf. In this case, simply remove it.
     * 2: The data has one child. In this case, simply replace the node with
     * the child node.
     * 3: The data has 2 children. For this assignment, use the predecessor to
     * replace the data you are removing, not the sucessor.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param data data to remove from the tree
     * @return the data removed from the tree.  Do not return the same data
     * that was passed in. Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("The AVL is empty so the data "
                    + "does not exist.");
        } else {
            AVLNode<T> dummyNode = new AVLNode<>(null);
            root = rRemove(data, root, dummyNode);
            return dummyNode.getData();
        }
    }

    /**
     * Recursive helper method for the remove method.
     *
     * @throws NoSuchElementException if the data is not found
     * @param data we are looking for to be removed
     * @param node that we are at and the node whose data we are comparing
     *             against to determine if/where we need to traverse.
     * @param dummyNode where the found node's data will be stored
     * @return the dummyNode so we can return the return its data in the
     * wrapper method.
     */
    private AVLNode<T> rRemove(T data, AVLNode<T> node, AVLNode<T> dummyNode) {
        if (node == null) {
            throw new NoSuchElementException("The data could not be found in "
                    + "the AVL");
        }
        if (node.getData().compareTo(data) < 0) {
            node.setRight(rRemove(data, node.getRight(), dummyNode));
        }
        if (node.getData().compareTo(data) > 0) {
            node.setLeft(rRemove(data, node.getLeft(), dummyNode));
        }
        if (node.getData().equals(data)) {
            dummyNode.setData(node.getData());
            if (node.getLeft() == null && node.getRight() == null) {
                size--;
                return null;
            }
            if (node.getLeft() != null && node.getRight() == null) {
                size--;
                return node.getLeft();
            }
            if (node.getLeft() == null && node.getRight() != null) {
                size--;
                return node.getRight();
            }
            if (node.getLeft() != null && node.getRight() != null) {
                AVLNode<T> predNode = new AVLNode<>(null);
                node.setLeft(rPredessor(node.getLeft(), predNode));
                node.setData(predNode.getData());
                size--;
            }
        }
        update(node);
        if (node.getBalanceFactor() < -1) {
            if (node.getRight() != null && node.getRight().
                    getBalanceFactor() > 0) {
                node.setRight(rRightRotate(node.getRight()));
            }
            node = rLeftRotate(node);
        }
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft() != null && node.getLeft().getBalanceFactor()
                    < 0) {
                node.setLeft(rLeftRotate(node.getLeft()));
            }
            node = rRightRotate(node);
        }
        return node;
    }

    /**
     * Recursive helper method for finding the predecessor
     *
     * @param node that we are at
     * @param predNode the predecessor where it is found
     * @return the predecessor
     */
    private AVLNode<T> rPredessor(AVLNode<T> node, AVLNode<T> predNode) {
        if (node.getRight() == null) {
            predNode.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(rPredessor(node.getRight(), predNode));
        }
        update(node);
        if (node.getBalanceFactor() < -1) {
            if (node.getRight() != null && node.getRight().
                    getBalanceFactor() > 0) {
                node.setRight(rRightRotate(node.getRight()));
            }
            node = rLeftRotate(node);
        }
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft() != null && node.getLeft().getBalanceFactor()
                    < 0) {
                node.setLeft(rLeftRotate(node.getLeft()));
            }
            node = rRightRotate(node);
        }
        return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data data to get in the AVL tree
     * @return the data in the tree equal to the parameter.  Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null.");
        }
        if (size == 0) {
            throw new NoSuchElementException("The AVL is empty, so the data "
                    + "does not exist.");
        } else {
            return rGet(data, root);
        }
    }

    /**
     * The recursive helper method for the get method
     *
     * @throws NoSuchElementException if the data is not found.
     * @param data that we are tring to get
     * @param node the node that we are currently, whose data we will compare
     *            against to determine if/where we need to traverse.
     * @return the data if it is found.
     */
    private T rGet(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("The data was not found in the "
                    + "AVL.");
        }
        if (node.getData().compareTo(data) < 0) {
            return rGet(data, node.getRight());
        }
        if (node.getData().compareTo(data) > 0) {
            return rGet(data, node.getLeft());
        }
        if (node.getData().equals(data)) {
            return node.getData();
        }
        throw new NoSuchElementException("The data was not found in the AVL");
    }

    /**
     * Returns whether or not the parameter is contained within the tree.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data data to find in the AVL tree
     * @return whether or not the parameter is contained within the tree
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null.");
        } else {
            return rContains(data, root);
        }
    }

    /**
     * The recursive helper method for the contains method
     *
     * @param data that we are looking for
     * @param node that we are currently at, and whose data we are comparing
     *             against the data to determine if/where we need to traverse.
     * @return where or not the data is in the tree.
     */
    private boolean rContains(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        }
        if (node.getData().compareTo(data) < 0) {
            return rContains(data, node.getRight());
        }
        if (node.getData().compareTo(data) > 0) {
            return rContains(data, node.getLeft());
        }
        if (node.getData().equals(data)) {
            return true;
        }
        return false;
    }

    /**
     * In your BST homework, you worked with the concept of the successor, the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node containing {@code data} whose left child is also
     * an ancestor of {@code data}.
     *
     * For example, in the tree below, the successor of 76 is 81, and the
     * successor of 40 is 76.
     *
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param data the data to find the successor of
     * @return the successor of {@code data}. If there is no larger data than
     * the one given, return null.
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null.");
        }
        if (size == 0) {
            throw new NoSuchElementException("The AVL is null so the data "
                    + "could not exist.");
        } else {
            return rSuccessor(root, data);
        }
    }

    /**
     * Recursive helper method that finds the node with the data we are
     * looking for. When we perform the first left traversal we store that
     * left node in a temp variable so that in case we run into the case
     * where our node containing the data whose successor doesn't have a
     * right node we we can return the node where the first left traversal
     * took place.
     *
     * @throws NoSuchElementException if the data is not found in the AVL
     * @param node whose data we are comparing against to determine if/where to
     *            traverse.
     * @param data that we are looking for.
     * @return the successor is found.
     */
    private T rSuccessor(AVLNode<T> node, T data) {
        if (node == null) {
            // if node is null the data does not exist in the AVL.
            throw new NoSuchElementException("The data was not found in the "
                    + "AVL.");
        } else if (node.getData().compareTo(data) < 0) {
            // if the node's data is less than the data that we are looking
            // for traverse right.
            return rSuccessor(node.getRight(), data);
        } else if (node.getData().compareTo(data) > 0) {
            // if the node's data is greater than the data that we are
            // looking for we need to traverse left.
            T successorFlag = rSuccessor(node.getLeft(), data);
            // we save this node as a flag to maybe return it later if the
            // node with data we are looking for doesn't have a right child.
            if (successorFlag == null) {
                return node.getData();
            } else {
                return successorFlag;
            }
        } else {
            // we are at the node with the data we are looking for
            if (node.getRight() == null) {
                // the node doesn't have a right child so return null
                return null;
            } else {
                // if the node does have a right child then proceed to find
                // the successor.
                return getMin(node.getRight());
            }
        }
    }

    /**
     * Recursive method that find the successor of the node containing the
     * data we were looking for.
     *
     * @param node that we are at, to determine if there is a left child that
     *            we can traverse to.
     * @return the successor of the node containing the data we were looking
     * for.
     */
    private T getMin(AVLNode<T> node) {
        if (node.getLeft() == null) {
            return node.getData();
        } else {
            return getMin(node.getLeft());
        }
    }

    /**
     * The left rotate method that will be called if the tree needs to be
     * rebalanced
     *
     * @param node where the tree needs to be rotated
     * @return the edited tree.
     */
    private AVLNode<T> rLeftRotate(AVLNode<T> node) {
        AVLNode<T> child = node.getRight();
        node.setRight(child.getLeft());
        child.setLeft(node);
        update(node);
        update(child);
        return child;
    }

    /**
     * The right rotated method that will be called if the tree needs to be
     * rebalanced
     *
     * @param node where the tree needs to be rotated
     * @return the edited tree.
     */
    private AVLNode<T> rRightRotate(AVLNode<T> node) {
        AVLNode<T> child = node.getLeft();
        node.setLeft(child.getRight());
        child.setRight(node);
        update(node);
        update(child);
        return child;
    }

    /**
     * The recursive update method that updates a nodes height and balance
     * factor.
     *
     * @param node that needs to be updated.
     */
    private void update(AVLNode<T> node) {
        int leftNodeHeight = rHeight(node.getLeft());
        int rightNodeHeight = rHeight(node.getRight());
        node.setHeight(rHeight(node));
        node.setBalanceFactor(leftNodeHeight - rightNodeHeight);
    }
    /**
     * Return the height of the root of the tree.
     * 
     * This method does not need to traverse the tree since this is an AVL.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * The recursive helper method for the height
     *
     * @param node whose height needs to be calculated
     * @return the height of the node
     */
    private int rHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return 1 + Math.max(rHeight(node.getLeft()), rHeight(node
                    .getRight()));
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Get the number of elements in the tree.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the root of the tree. Normally, you wouldn't do this, but it's
     * necessary to grade your code.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
