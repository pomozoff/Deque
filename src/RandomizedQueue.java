import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public final class RandomizedQueue<Item> implements Iterable<Item> {
    private int count = 0;
    private Item[] array = (Item[]) new Object[1];

    /// ***********************************************************************

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int currentIndex = 0;
        private final int[] shuffledIndices;

        RandomizedQueueIterator() {
            int length = size();
            shuffledIndices = new int[length];

            for (int i = 0; i < length; i++) {
                shuffledIndices[i] = i;
            }

            for (int i = 0; i < length; i++) {
                int x = StdRandom.uniform(length - i);
                int idx = x + i;

                int tmp = shuffledIndices[idx];
                shuffledIndices[idx] = shuffledIndices[i];
                shuffledIndices[i] = tmp;
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size();
        }

        @Override
        public Item next() {
            if (currentIndex >= size()) {
                throw new NoSuchElementException();
            }

            int index = shuffledIndices[currentIndex++];

            return array[index];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /// ***********************************************************************

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (count == array.length) {
            resize(array.length * 2);
        }
        array[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniform(count);
        Item result = array[randomIndex];

        if (randomIndex < count - 1) {
            array[randomIndex] = array[count - 1];
        }
        array[--count] = null;

        if (count >= 4 && count == array.length / 4) {
            resize(array.length / 2);
        }

        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(count);
        return array[index];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }

    /// ***********************************************************************

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        final int count = 10;
        for (int i = 0; i < count; i++) {
            int value = StdRandom.uniform(100);
            System.out.println("put: " + value);
            queue.enqueue(value);
        }

        System.out.println("-----------------");

        int number = 0;
        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()) {
            number++;
            System.out.println("get: " + iterator.next());
        }
        assert queue.size() == count;
        assert queue.size() == number;

        System.out.println("-----------------");

        for (int i = 0; i < 3; i++) {
            System.out.println("deq: " + queue.dequeue());
        }
        assert queue.size() == 7;

        System.out.println("-----------------");

        iterator = queue.iterator();
        number = 0;
        while (iterator.hasNext()) {
            number++;
            System.out.println("get: " + iterator.next());
        }
    }
}
