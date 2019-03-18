/**
 * Your implementation of a non-circular singly linked list with a tail pointer.
 *
 * @author Balkrishna Patel
 * @userid bpatel66
 * @GTID 903023192
 * @version 1.0
 */
public class SinglyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index that has been "
                   + "provided is out of the bounds of the list please "
                   + "provide another");
        }
        if (data == null) {
            throw new IllegalArgumentException("The data that has been "
                    + "passed in is null please edit the data.");
        }
        LinkedListNode<T> newNode = new LinkedListNode<>(data, null);
        if (size == 0) {
            head = new LinkedListNode<>(data, head);
            tail = head;
            size++;
            return;
        }
        if (index == size) {
            tail.setNext(newNode);
            tail = newNode;
            size++;
            return;
        }
        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            size++;
        } else {
            LinkedListNode<T> currentNode = head;
            int counter = 0;
            while (counter + 1 != index) {
                currentNode = currentNode.getNext();
                counter++;
            }
            newNode.setNext(currentNode.getNext());
            currentNode.setNext(newNode);
            size++;
        }
        /*if (size == 0) {
            head = new LinkedListNode<>(data, head);
            tail = head;
            size++;
            return;
        }
        if (index == size) {
            LinkedListNode<T> newNode = new LinkedListNode<>(data, null);
            tail.setNext(newNode);
            tail = newNode;
            size++;
            return;
        }
        if (index == 0) {
            head = new LinkedListNode<>(data, head);
            size++;
            return;
        } else{
            head = rAddAtIndex(head, index, data);
            size++;
        }*/
    }
    /*private LinkedListNode<T> rAddAtIndex(LinkedListNode<T> node, int index,
                                          T data) {
        if (index == 0) {
            return new LinkedListNode<>(data, node);
        }
        if (node == null) {
            throw new IndexOutOfBoundsException("index is negative or greater"
             + " than size");
        }
        node.setNext(rAddAtIndex(node.getNext(), index - 1, data));
        return node;
    }*/

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that has been passed"
                    + " in is null please edit the data.");
        }
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that has been passed"
                    + " in is null please edit the data.");
        }
        if (size == 0) {
            head = new LinkedListNode<>(data, null);
            //size++;
        }
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index that has been "
                    + "provided is out of the bounds of the list please "
                    + "provide another index.");
        }
        T tempData;
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            tempData = head.getData();
            head = null;
            tail = null;
            size--;
            return tempData;
        }
        if (index == 0) {
            tempData = head.getData();
            head = head.getNext();
            size--;
            return tempData;
        }
        if (index == size - 1) {
            LinkedListNode<T> currentNode = head;
            while (currentNode.getNext().getNext() != null) {
                currentNode = currentNode.getNext();
            }
            tempData = currentNode.getNext().getData();
            tail = currentNode;
            tail.setNext(null);
            size--;
            return tempData;
        } else {
            LinkedListNode<T> currentNode = head;
            int counter = 0;
            while (counter + 1 != index) {
                currentNode = currentNode.getNext();
                counter++;
            }
            tempData = currentNode.getNext().getData();
            currentNode.setNext(currentNode.getNext().getNext());
            size--;
            return tempData;
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if(size == 0) {
            return null;
        } else {
            return removeAtIndex(0);
        }
        //return removeAtIndex(0);
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        if (size == 0) {
            return null;
        } else {
            return removeAtIndex(size - 1);
        }
        //return removeAtIndex(size - 1);
    }

    /**
     * Returns the index of the first occurrence of the passed in data in the
     * list or -1 if it is not in the list.
     *
     * If data is in the head, should be O(1). In all other cases, O(n).
     *
     * @param data the data to search for
     * @throws java.lang.IllegalArgumentException if data is null
     * @return the index of the first occurrence or -1 if not in the list
     */
    public int indexOf(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that has been passed"
                    + " in is null please edit the data.");
        }
        if (size == 0) {
            return -1;
        }
        LinkedListNode<T> currentNode = head;
        if (head.getData() == data) {
            return 0;
        }
        for (int i = 0; i < size; i++) {
            if (currentNode.getData().equals(data)) {
                return i;
            } else {
                currentNode = currentNode.getNext();
            }
        }
        return -1;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting the head and tail should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index that has been "
                    + "provided is out of the bounds of the list please "
                    + "provide another index.");
        }
        if (index == 0) {
            return head.getData();
        }
        if (index == size - 1) {
            return tail.getData();
        } else {
            LinkedListNode<T> currentNode = head;
            int counter = 0;
            while (counter != index) {
                currentNode = currentNode.getNext();
                counter++;
            }
            return currentNode.getData();
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        } else {
            Object[] arrayOfData = new Object[size];
            LinkedListNode<T> currentNode = head;
            for (int i = 0; i < size; i++) {
                T currentNodeData = currentNode.getData();
                arrayOfData[i] = currentNodeData;
                currentNode = currentNode.getNext();
            }
            return arrayOfData;
        }
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list of all data and resets the size.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
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
     * Returns the head node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked list
     */
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}