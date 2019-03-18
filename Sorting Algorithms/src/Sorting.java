import java.util.ArrayList;
import java.util.Comparator;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Balkrishna Patel
 * @userid bpatel66
 * @GTID 903023192
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *  adaptive
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        /* if the array or the comparator that is passed in is null throw
            an exception */
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array that was passed in "
                    + "is empty or the comparator that was passed in is null.");
        }
        //int length = arr.length;
        /* i is the iteration that we are on, we keep track of this because
        after iteration 0 the we know that the largest data has been bubbled
        up the top, and after iteration 1 we know that the second largest
        data is bubbled up to the top */
        int i = 0;
        /* create a boolean flag so that if at the end of an iteration a swap
         was not made we can end the process because the array is in the
         correct order, we set it true because we need to perform at least
         one iteration of of the array */
        boolean swapMade = true;
        /* while the number of iterations is less than the array's length and
         a swap was made in the previous iteration continue the procedure */
        while (i < arr.length && swapMade) {
            /* set the boolean flag to false before we iterate through the
            array so that if can end the process early if no swap is made */
            swapMade = false;
            /* this for loop will iterate through the array while j is less
            than the array's length - the number of iterations that have been
             performed */
            for (int j = 0; j < arr.length - i - 1; j++) {
                /* if the data at j is greater than the data next to it swap
                the two data's and set the boolean flag to true */
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    T temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                    swapMade = true;
                }
            }
            /* increment i so that we can stop short in the next iteration of
             the loop if we need to iterate through the loop again */
            i++;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *  adaptive
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        /* if the array or the comparator that was passed in is null then
        throw an illegalArguement Exception*/
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array that was passed in "
                    + "is empty or the comparator that was passed in is null.");
        }
        /* we start at i = 1 because it is assumed the first element is
        already sorted */
        for (int i = 1; i < arr.length; i++) {
            /* we initialize j to i because that is the first element we will
             become comparing */
            int j = i;
            /* while j is greater than one we know we haven't reached the
            beginning of the sorted array AND if the element at j  is less
            than j - 1 then we need to swap else the elements are inorder for
             that iteration */
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                /* if both conditions are met then we need to swap the
                elements a j and j - 1 and decrement j so that we can move
                the element at j into the correct position */
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        /* if the array or the comparator that was passed in is null then
        throw an illegalArguementException */
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array that was passed in "
                    + "is empty or the comparator that was passed in is null.");
        }

        //int length = arr.length;
        /* if the array's length is great than 1 element */
        if (arr.length > 1) {
            /* find the middle index of the array */
            int midIndex = arr.length / 2;
            /* create the left array of the size of the middle of the passed
            in array */
            T[] leftArr = (T[]) new Object[midIndex];
            /* create the right array the size of the passed in array and the
            middle of the passed in array */
            T[] rightArr = (T[]) new Object[arr.length - midIndex];
            /* place the first half of the elements from the passed in array
            into the new left array */
            for (int i = 0; i < midIndex; i++) {
                leftArr[i] = arr[i];
            }
            /* place the second half of the elements from the passed in array
             into the new right array */
            for (int i = midIndex; i < arr.length; i++) {
                rightArr[i - midIndex] = arr[i];
            }
            /*call mergeSort on the left array to divide up the left array
            first*/
            mergeSort(leftArr, comparator);
            /*call mergerSort on the right array to divide up the right array
             second*/
            mergeSort(rightArr, comparator);
            /*set the left array's index to to 0*/
            int leftIndex = 0;
            /*set the right array's index to 0*/
            int rightIndex = 0;
            /*set the current index to 0*/
            int currIndex = 0;
            /*while the left index is less than the mid point of the array
            and the right index is less than the array's length - the mid
            point*/
            while (leftIndex < midIndex && rightIndex
                    < arr.length - midIndex) {
                /* if the element at the left array's left index is less
                than or equal (for stability) the element at the right
                array's current index add the left element into the array and
                 increment the left array's
                 index*/
                if (comparator.compare(leftArr[leftIndex],
                        rightArr[rightIndex]) <= 0) {
                    arr[currIndex] = leftArr[leftIndex];
                    leftIndex++;
                /* else add the right element into the array and increment
                the right arrays index*/
                } else {
                    arr[currIndex] = rightArr[rightIndex];
                    rightIndex++;
                }
                /* increment the current array's index so that the next
                element can be merged */
                currIndex++;
            }
            /* if the right array was full merged into the parent array merge
             the left array into the parent array */
            while (leftIndex < midIndex) {
                arr[currIndex] = leftArr[leftIndex];
                currIndex++;
                leftIndex++;
            }
            /* if the left array was fully merged into the parent array
            merger the right array into the parrent array*/
            while (rightIndex < arr.length - midIndex) {
                arr[currIndex] = rightArr[rightIndex];
                currIndex++;
                rightIndex++;
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        /* if the array that was passed in is null throw
        illegalArgumentException */
        if (arr == null) {
            throw new IllegalArgumentException("The array that was passed in "
                    + "is null.");
        }
        /*store the longest length of all the integers in the array*/
        int iterations = 0;
        /*loop through the entire array to find the longest length of the
        integer*/
        for (int i = 0; i < arr.length; i++) {
            int newLength = elementLength(arr[i]);
            if (newLength > iterations) {
                iterations = newLength;
            }
        }
        /*power will be used to reduce a number after one iteration is
        complete so that we sort the int (131 / 1 = 1, 131 / 10 = 13,
        131 / 100 = 1)*/
        int power = 1;
        /*add and the ints to and from the buckets until the longest int has
        been sorted*/
        for (int i = 0; i < iterations; i++) {
            /*create the buckets to sort the ints into*/
            ArrayList<Integer>[] buckets = new ArrayList[19];
            /*for each element in the arr*/
            for (int j = 0; j < arr.length; j++) {
                /*shave off the least significant digit leaving only the part
                 of the int that needs to be sorted this iteration*/
                int reducedInt = arr[j] / power;
                /*find the index that the int needs to be put into*/
                int bucketIndex = reducedInt % 10;
                /*if the bucket at that index is empty put a arraylist in
                that position*/
                if (buckets[bucketIndex + 9] == null) {
                    buckets[bucketIndex + 9] = new ArrayList<>();
                }
                /*add the int to that bucket at the index that was calculated*/
                buckets[bucketIndex + 9].add(arr[j]);
            }
            /*create a variable to index into the array that was passed in.*/
            int arrIndex = 0;
            /*iterate through the buckets and take the elements and put them
            in array in the order they are taken out of the buckets*/
            for (int l = 0; l < buckets.length; l++) {
                if (buckets[l] != null) {
                    for (int j = 0; j < buckets[l].size(); j++) {
                        arr[arrIndex] = buckets[l].get(j);
                        arrIndex ++;
                    }
                }
            }
            /*multiply the power by 10 to we can shave off the lsd we just
            worked on.*/
            power *= 10;
        }
    }

    /**
     * Method to help find the length of a passed in integer.
     *
     * @param element of the array who length we need to find
     * @return the length of the element that was passed in.
     */
    private static int elementLength(int element) {
        int length = 0;
        while (element != 0) {
            length++;
            element /= 10;
        }
        return length;
    }
}
