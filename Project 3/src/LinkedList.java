// Written by Liam Kinney, kinne351
public class LinkedList<T extends Comparable<T>> implements List<T> {
    Node<T> head;
    Node<T> ptr;
    Node<T> trailer;
    private int size;
    private boolean isSorted;

    public LinkedList(){
        head = null;
        size = 0;
        isSorted = true;
    }

    @Override
    public boolean add(T element) {
        if(element == null)
            return false;

        Node<T> node = new Node<T>(element);

        if(head == null)
            head = node;
        else {
            Node<T> current = head;
            while(current.getNext() != null) // Traversing through until it gets to the end
                current = current.getNext();
            current.setNext(node);
        }
        size++;
        checkSorted(); // Checks if the list is sorted in ascending order, implemented down below
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if(element == null || index < 0 || index >= size)
            return false;

        Node<T> node = new Node<T>(element);

        if(index == 0) {
            node.setNext(head);
            head = node;
        }
        else {
            ptr = head;
            trailer = null;
            for (int i = 0; i < index; i++) {
                trailer = ptr;
                ptr = ptr.getNext();
            }
            node.setNext(ptr);
            trailer.setNext(node);
        }

        size++;
        checkSorted();
        return true;
    }

    @Override
    public void clear() {
        head = null; // Sets every other node to null because of Java's automatic garbage collection
        size = 0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size)
            return null;

        ptr = head;
        for(int i = 0; i < index; i++)
            ptr = ptr.getNext();
        if(ptr == null)
            return null;
        return ptr.getData();
    }

    @Override
    public int indexOf(T element) {
        ptr = head;
        int counter = 0;
        for(int i = 0; i < size; i++){
            if(ptr.getData().compareTo(element) == 0)
                return counter;
            ptr = ptr.getNext();
            counter++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void sort() {
        if(isSorted)
            return;

        ptr = head;
        head = null;
        while(ptr != null){
            Node<T> n = ptr.getNext();
            insert(ptr);
            ptr = n;
        }
        isSorted = true;
    }

    // Helper function for sort, finds the correct place to insert a node
    public void insert(Node<T> node) {
        if(head == null || head.getData().compareTo(node.getData()) >= 0){ // Checks if list is empty, or if it needs to be inserted at the head
            node.setNext(head);
            head = node;
        }
        else { // Iterates over the list to find the correct position,
            Node<T> curr = head;
            while(curr.getNext() != null && curr.getNext().getData().compareTo(node.getData()) < 0)
                curr = curr.getNext();

            node.setNext(curr.getNext());
            curr.setNext(node);
        }
    }

    @Override
    public T remove(int index) {
        if(index < 0 || index >= size || head == null)
            return null;

        T removed = null;
        ptr = head;
        Node<T> prev = null;
        if(index == 0) {
            removed = ptr.getData();
            head = head.getNext();
            size--;
            return removed;
        }

        for(int i = 0; i < index; i++) {
            prev = ptr;
            ptr = ptr.getNext();
        }

        removed = ptr.getData();
        prev.setNext(ptr.getNext());

        size--;
        checkSorted();
        return removed;
    }

    @Override
    public void equalTo(T element) {
        if(element == null)
            return;

        int updateSize = 0;
        ptr = head;
        trailer = null;

        while(ptr != null){
            if(ptr.getData().compareTo(element) == 0) {
                updateSize++;
                if(trailer != null)
                    trailer.setNext(ptr);

                trailer = ptr;
                if(head == ptr) head = ptr;
            }
            else if(trailer != null)
                trailer.setNext(ptr.getNext());
            else
                head = ptr.getNext();

            ptr = ptr.getNext();
        }

        if(trailer != null)
            trailer.setNext(null);
        else
            head = null;


        size = updateSize;
        checkSorted();
    }

    @Override
    public void reverse() {
        Node<T> prev = null;
        while(head != null){
            Node<T> next = head.getNext();
            head.setNext(prev);
            prev = head;
            head = next;
        }
        // First index will be pointed to null as it will be the last index, and then the next
        // index will be the new head, and so on until the list is reversed with null at the end.

        head = prev;

        checkSorted();
    }

    @Override
    // The merge method compares the first node of each linked list and assigns the smaller one as the head
    // of the new merged list. It then continues to compare the next node with the other node that was compared
    // before and assigns the smaller one in that comparison to be the next node in the merged list.
    public void merge(List<T> otherList) {
        if(otherList == null)
            return;

        LinkedList<T> other = (LinkedList<T>) otherList;
        sort();
        other.sort();

        ptr = head;
        Node<T> otherPtr = other.head;

        if (ptr == null) {
            head = otherPtr;
            return;
        }

        if (otherPtr == null)
            return;

        if (ptr.getData().compareTo(otherPtr.getData()) > 0) {
            head = otherPtr;
            otherPtr = otherPtr.getNext();
        }
        else
            ptr = ptr.getNext();

        Node<T> mergedList = head;

        while (ptr != null && otherPtr != null) {
            if (ptr.getData().compareTo(otherPtr.getData()) > 0) {
                mergedList.setNext(otherPtr);
                otherPtr = otherPtr.getNext();
            }
             else {
                mergedList.setNext(ptr);
                ptr = ptr.getNext();
            }

            mergedList = mergedList.getNext();
        }

        if(ptr == null)
            mergedList.setNext(otherPtr);
        else
            mergedList.setNext(ptr);

        size = size + other.size;
        isSorted = true;
    }

    @Override
    public boolean rotate(int n) {
        if(n <= 0 || size <= 1)
            return false;

        n %= size; // Finding actual amount of rotations

        if(n == 0)
            return true;

        ptr = head;
        for(int i = 1; i < size - n; i++)
            ptr = ptr.getNext();

        // Making the list circular, by making the tail the head of the list
        Node<T> tail = getTail();
        tail.setNext(head);
        head = ptr.getNext();
        ptr.setNext(null);

        checkSorted();
        return true;
    }

    public Node<T> getTail(){ // Gets the tail of the linked list, the last node, used by rotate
        if(head == null)
            return null;

        Node<T> tail = head;
        while(tail.getNext() != null)
            tail = tail.getNext();

        return tail;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        ptr = head;
        while(ptr != null){
            result.append(ptr.getData().toString());
            if(ptr.getNext() != null)
                result.append(" ");
            ptr = ptr.getNext();
        }
        return result.toString();
    }

    @Override
    public boolean isSorted() {
        checkSorted();
        return isSorted;
    }

    public void checkSorted(){
        isSorted = true;
        ptr = head;
        while(ptr != null && ptr.getNext() != null) {
            if (ptr.getData().compareTo(ptr.getNext().getData()) > 0) {
                isSorted = false;
                return;
            }
            ptr = ptr.getNext();
        }
    }

}
