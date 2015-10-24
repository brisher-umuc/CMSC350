/**
 * Abstract:
 *
 * ArrayStack(int)
 *  T get(int)
 *  void set(int, T)
 *  private void add(int, T)
 *  private T remove(int)
 *  private void resize()
 *  int getsize()
 *  void push(T)
 *  T pop()
 *
 */

public class ArrayStack <T> {
    private int size;
    private T[] array;

    /**
     * Constructor to build a new Object array
     *
     * @param n the number of elements in the array
     */
    ArrayStack(int n) {
        if (n < 0) {
            throw new NegativeArraySizeException();
        }
        array = (T[]) new Object[n];
        size = 0;
    }

    /**
     * Convenience function to get the size of the array
     *
     * @return size of array
     */
    public int getSize() {
        return size;
    }

    /**
     * Convenience function to get the capacity of the array
     *
     * @return capacity of the array
     */
    public int getCapacity() {
        return array.length;
    }

    /**
     * Returns true/false based on if the stack is empty or not
     *
     * @return boolean - stack is empty or not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Set the item at position i to obj
     *
     * @param i the index of the item to alter
     * @param obj the item to set the array's element at position i to
     */
    public void set(int i, T obj) {
        array[i] = obj;
    }

    /**
     * Return the representation of whatever object is stored at position i
     *
     * @param i the index of the item to return
     * @return the item at index i
     */
    private T get(int i) {
        return array[i];
    }

    /**
     * Add an element at position i
     *
     * @param i position in the array to add an item to
     * @param obj the object to be added
     */
    private void add(int i, T obj) {
        if (i < 0 || i > array.length) {
            throw new IndexOutOfBoundsException();
        }
        else if (size == array.length) {
            resize(2 * array.length);
        }
        for (int j = size; j > i; j--) {
            array[j] = array[j - 1];
        }
        array[i] = obj;
        size++;
    }

    /**
     * Resize the array, called when the array isn't large enough to support an addition and
     * when array capacity is 3x larger than the size
     */
    private void resize(int n) {
        T[] newArray = (T[]) new Object[n];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    /**
     * Remove the object at position i from the array
     *
     * @param i the position of the object to remove
     * @return the object that has been removed
     */
    private T remove(int i) {
        T obj = array[i];
        size--;
        array[i] = null;

        if (array.length >= 3 * size) {
            resize(array.length / 2);
        }

        return obj;
    }

    /**
     * Push the object onto the stack by wrapping add()
     *
     * @param obj the object to push onto the stack
     */
    public void push(T obj) {
        add(size, obj);
    }

    /**
     * Pop an object off the stack by wrapping remove()
     */
    public T pop() {
        return remove(size - 1);
    }

    /**
     * Peek at the object on the top of the stack
     */
    public T peek() {
        return get(size - 1);
    }

    public static void test() {
        ArrayStack arraystack = new ArrayStack(5);
        assert arraystack.getSize() == 0;
        assert arraystack.getCapacity() == 5;

        assert arraystack.isEmpty();

        arraystack.push("zero");
        arraystack.push("one");
        arraystack.push(2);
        arraystack.push('A');
        arraystack.push(3.3);  // last in

        assert !arraystack.isEmpty();  // isEmpty works

        System.out.println(arraystack.peek());  // peek prints 3.3

        arraystack.push("resize the array");
        assert arraystack.getCapacity() == 10;  // array capacity doubled

        assert arraystack.pop().equals("resize the array");  // first out
        assert (Double)arraystack.pop() == 3.3;
        assert (Character)arraystack.pop() == 'A';
        assert arraystack.getCapacity() == 5;  // capacity was >= 3*size, capacity is now half of what it was
        assert arraystack.getSize() == 3;

        for (int i = arraystack.getSize(); i > 0; i--) {
            arraystack.pop();
        }
        assert arraystack.isEmpty();
        assert arraystack.getCapacity() == 1;  // resized itself down to 1

        arraystack.push('A');
        arraystack.push("force a resize up again");
        assert arraystack.getCapacity() == 2;  // capacity doubled
    }

    public static void main(String[] args) {
        test();
        System.out.println("Test ran successfully.");
    }
}
