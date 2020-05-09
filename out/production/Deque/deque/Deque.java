import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    /// ******************************************************************************************************

    private Node first, last;
    private int n = 0;

    private class Node {
        Item item;
        Node previous;
        Node next;
    }

    /// ******************************************************************************************************

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (last == null) {
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

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {  return new DequeIterator(); }

    /// ******************************************************************************************************

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
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

        n++;
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

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Node result = first;
        first = first.next;

        n--;
        return result.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }

        Node result = last;
        last = last.previous;

        n--;
        return result.item;
    }

    /// ******************************************************************************************************

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(2);
        deque.addLast(3);
        deque.addFirst(1);
        deque.addLast(4);
        deque.addLast(5);

        for (Integer value: deque) {
            System.out.println(value); // 1 2 3 4 5
        }

        System.out.println(deque.removeFirst()); // 1
        System.out.println(deque.removeLast());  // 5
        System.out.println(deque.removeLast());  // 4
        System.out.println(deque.removeFirst()); // 2
        System.out.println(deque.removeLast());  // 3
    }
}
