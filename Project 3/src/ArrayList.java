// Written by Liam Kinney, kinne351
public class ArrayList<T extends Comparable<T>> implements List<T> {
    T[] a;
    private int size;
    private boolean isSorted;

    public ArrayList(){
        size = 0;
        isSorted = true;
        a = (T[]) new Comparable[2];
    }
    @Override
    public boolean add(T element) {
        if(element == null)
            return false;
        if(size >= a.length)
            resize();

        a[size++] = element;

        checkSorted(); // Checks if the array is sorted and updates isSorted, implemented down below

        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if(element == null || index < 0 || index >= size)
            return false;

        if(size >= a.length)
            resize();

        for(int i = a.length - 2; i >= index; i--)
            a[i + 1] = a[i];
        a[index] = element;
        size++;

        checkSorted();

        return true;
    }

    @Override
    public void clear() {
        a = (T[]) new Comparable[a.length];
        isSorted = true;
        size = 0;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size)
            return null;
        return a[index];
    }

    @Override
    public int indexOf(T element) {
        if(isSorted)
            return binarySearch(element); // Binary search to increase efficiency if the array is sorted, implemented down below
        for(int i = 0; i < a.length; i++)
            if(a[i] != null)
                if(a[i].compareTo(element) == 0)
                    return i;
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
        for(int i = 1; i < a.length; i++) {
            T temp = a[i];
            int leftIndex = i - 1;

            while(leftIndex >= 0 && temp != null && a[leftIndex].compareTo(temp) > 0) {
                a[leftIndex + 1] = a[leftIndex];
                leftIndex--;
            }
            a[leftIndex + 1] = temp;
        }
        isSorted = true;
    }

    @Override
    public T remove(int index) {
        if(index < 0 || index >= size)
            return null;
        T removed = a[index];
        a[index] = null;
        for(int i = index; i < size; i++)
            a[i] = a[i + 1];

        a[size - 1] = null; // Avoiding duplicate reference to the last element
        size--;

        checkSorted();

        return removed;
    }

    @Override
    public void equalTo(T element) {
        //if(isSorted){ for increased efficiency
            //TODO: use indexOf and then instead of setting every other element to null, just change the size
        //} did not have time to implement :/
        if(element == null)
            return;
        int right = 0;
        for(int i = 0; i < size; i++){
            if(a[i] == null) continue; // This skips the null elements

            if(a[i].compareTo(element) == 0) // If they are equal
                a[right++] = a[i];
        }
        for(int i = right; i < size; i++)
            a[i] = null; // Sets remaining elements to null

        size = right;

        checkSorted();
    }

    @Override
    public void reverse() {
        for (int i = 0; i < size / 2; i++) {
            T temp = a[i];
            a[i] = a[size - i - 1];
            a[size - i - 1] = temp;
        }
        isSorted = false;
    }

    @Override
    public void merge(List<T> otherList) {
        if(otherList == null)
            return;

        ArrayList<T> other = (ArrayList<T>) otherList;
        sort();
        other.sort();

        T[] mergedList = (T[]) new Comparable[size + other.size];

        // Compares the first index of both lists (because they're both in order so those are the smallest possible
        // elements) and add the smaller one to the start of the mergedList. Then compares the larger one with the next
        // index of the other list
        int i = 0, j = 0, k = 0;
        while(i < size && j < other.size){
            if(a[i].compareTo(other.get(j)) <= 0){
                mergedList[k] = a[i];
                i++;
            }
            else {
                mergedList[k] = other.get(j);
                j++;
            }

            k++;
        }

        // Fills in the remaining elements in case the sizes for the two arrays are not the same
        while(i < size){
            mergedList[k] = a[i];
            i++;
            k++;
        }

        while(j < other.size){
            mergedList[k] = other.get(j);
            j++;
            k++;
        }

        size = size + other.size;
        a = mergedList;
        isSorted = true;
    }

    @Override
    public boolean rotate(int n) {
        if(n <= 0 || a.length <= 1 || size <= 2)
            return false;

        n %= size; // Number of rotations simplified, will not change n unless n == size or n > size

        for(int i = 0; i < n; i++){
            T temp = a[size - 1];
            for(int k = size - 1; k > 0; k--)
                a[k] = a[k - 1];
            a[0] = temp;
        }  // Iterates through the array, puts last index to the front and
          // shifts every other element to the right, and repeats n times

        checkSorted();
        return true;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for (T t : a){
            if(t != null)
                result.append(t.toString()).append(" ");
        }
        return result.toString();
    }

    @Override
    public boolean isSorted() {
        checkSorted();
        return isSorted;
    }

    public void resize() {
        T[] newArray = (T[]) new Comparable[a.length * 2];
        System.arraycopy(a, 0, newArray, 0, a.length);
        a = newArray;
    }

    public int binarySearch(T value){
        int start = 0;
        int end = size - 1;

        while(start <= end){
            int middle = (start + end) / 2;
            T midValue = a[middle];

            if(value != null && midValue != null && value.compareTo(midValue) == 0){
                // Decreasing middle to find the very first index of the element
                while((middle - 1) >= 0 && value.compareTo(a[middle - 1]) == 0)
                        middle--;
                return middle;
            }
            if(value != null && midValue != null && value.compareTo(midValue) < 0)
                end = middle - 1;
            else
                start = middle + 1;
        }
        return -1;
    }

    public void checkSorted(){
        isSorted = true;
        for(int i = 0; i < size - 1; i++)
            if (a[i].compareTo(a[i + 1]) > 0) {
                isSorted = false;
                return;
            }
    }
}
