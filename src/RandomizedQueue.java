import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] array;

    /// ***********************************************************************

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        array = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == array.length) {
            resize(array.length * 2);
        }
        array[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniform(size);
        Item result = array[randomIndex];

        if (randomIndex < size - 1) {
            array[randomIndex] = array[size - 1];
        }
        array[--size] = null;

        if (size >= 4 && size == array.length / 4) {
            resize(array.length / 2);
        }

        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return array[StdRandom.uniform(size)];
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

    /// ***********************************************************************

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int index = 0;
        private final int[] shuffledIndices;

        RandomizedQueueIterator() {
            final int length = size();
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
            return index < size();
        }

        @Override
        public Item next() {
            if (index >= size()) {
                throw new NoSuchElementException();
            }

            int nextIndex = shuffledIndices[this.index++];

            return array[nextIndex];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }
}
