import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int lastIndex = 0;

    /// ******************************************************************************************************

    private Item[] array = (Item[]) new Object[2];
    private Stack<Integer> availableIndexes = new Stack(2);

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }

    private int randomIndex() {
        return StdRandom.uniform(lastIndex);
    }

    /// ******************************************************************************************************

    private class Stack<Item> {
        private Item[] stackArray;
        private int n = 0;

        private void resizeStackArray(int capacity) {
            Item[] copy = (Item[]) new Object[capacity];
            for (int i = 0; i < stackArray.length; i++) {
                copy[i] = stackArray[i];
            }
            stackArray = copy;
        }

        public Stack(int capacity) {
            stackArray = (Item[]) new Object[capacity];
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public void push(Item item) {
            if (n == stackArray.length) {
                resizeStackArray(stackArray.length * 2);
            }
            stackArray[n++] = item;
        }

        public Item pop() {
            Item result = stackArray[--n];

            stackArray[n] = null;
            if (n > 4 && n == stackArray.length / 4) {
                resize(stackArray.length / 2);
            }

            return result;
        }
    }

    /// ******************************************************************************************************

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < lastIndex;
        }

        @Override
        public Item next() {
            if (currentIndex == lastIndex) {
                throw new NoSuchElementException();
            }

            Item result = array[currentIndex];
            currentIndex++;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

    /// ******************************************************************************************************

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return lastIndex < 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return lastIndex + 1;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        int targetIndex;
        if (availableIndexes.isEmpty()) {
            targetIndex = lastIndex++;
        } else {
            targetIndex = availableIndexes.pop();
        }

        if (targetIndex == array.length - 1) {
            resize(array.length * 2);
        }

        array[targetIndex] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item result;
        int randomIndex;

        do {
            randomIndex = randomIndex();
            result = array[randomIndex];
        } while (result == null);

        if (randomIndex < lastIndex - 1) {
            availableIndexes.push(randomIndex);
        } else {
            lastIndex--;
        }

        array[randomIndex] = null;
        if (lastIndex > 4 && lastIndex == array.length / 4) {
            resize(array.length / 2);
        }


        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        final int randomIndex = randomIndex();
        return array[randomIndex];
    }

    /// ******************************************************************************************************

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomQueue = new RandomizedQueue<>();

        randomQueue.enqueue(2);
        randomQueue.enqueue(3);
        randomQueue.enqueue(1);
        randomQueue.enqueue(4);
        randomQueue.enqueue(5);

        for (Integer value: randomQueue) {
            System.out.println(value);
        }

        System.out.println("-----------------");

        for (Integer value: randomQueue) {
            System.out.println(randomQueue.sample());
        }

        System.out.println("-----------------");

        System.out.println(randomQueue.dequeue());
        randomQueue.enqueue(8);
        System.out.println(randomQueue.dequeue());
        System.out.println(randomQueue.dequeue());
        System.out.println(randomQueue.dequeue());
        randomQueue.enqueue(6);
        System.out.println(randomQueue.dequeue());
        randomQueue.enqueue(7);
    }
}
