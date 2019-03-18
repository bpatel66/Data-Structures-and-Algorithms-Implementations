import java.util.NoSuchElementException;

/**
 * Your implementation of a linked deque.
 *
 * @author Balkrishna Patel
 * @userid bpatel66
 * @GTID 903023192
 * @version 1.0
 */
public class LinkedDeque<T> {
    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null please pass in new valid data.");
        }
        if (size == 0) {
            LinkedNode<T> newNode = new LinkedNode<>(data, null, null);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            LinkedNode<T> newNode =
                    new LinkedNode<>(data, null, null);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
        }
    }

    /**
     * Adds the data to the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that was passed in "
                    + "is null please pass in new valid data.");
        }
        if (size == 0) {
            addFirst(data);
        } else  {
            LinkedNode<T> newNode =
                    new LinkedNode<>(data, tail, null);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes the data at the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The Deque is empty, cannot "
                    + "remove elements that do not exist.");
        }
        if (size == 1) {
            T temp = head.getData();
            head = null;
            tail = null;
            size--;
            return temp;
        } else {
            T temp = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return temp;
        }
    }

    /**
     * Removes the data at the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The Deque is empty, cannot "
                    + "remove elements that do not exist.");
        }
        if (size == 1) {
            return removeFirst();
        } else  {
            T temp = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp;
        }
    }

    /**
     * Returns the number of elements in the list.
     *
     * Runs in O(1) for all cases.
     * 
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}