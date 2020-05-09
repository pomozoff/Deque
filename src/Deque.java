import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    /// ***********************************************************************

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;

        first = new Node();
        first.item = item;
        first.next = oldFirst;

        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.previous = first;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldLast = last;

        last = new Node();
        last.item = item;
        last.previous = oldLast;

        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Node result = first;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.previous = null;
        }

        size--;
        return result.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }

        Node result = last;
        last = last.previous;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }

        size--;
        return result.item;
    }

    /// ***********************************************************************

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addLast(1);
        for (Integer value: deque) {
            StdOut.println(value);
        }

        assert deque.removeLast() == 1;
        for (Integer value: deque) {
            StdOut.println(value);
        }
    }

    /// ***********************************************************************

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Node result = current;
            current = current.next;

            return result.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        private Item item;
        private Node previous;
        private Node next;
    }
}
