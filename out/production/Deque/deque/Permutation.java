import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
//    private Deque<String> deque = new Deque<>();
//    private RandomizedQueue<String> randomQueue = new RandomizedQueue<>();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("One argument is required");
            return;
        }

        int k = 0;
        try {
            k = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Argument '" + args[0]
                    + "' must be an integer.");
            return;
        }

        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            randomQueue.enqueue(string);
        }

        for (int i = 0; i < k; i++) {
            String value = randomQueue.dequeue();
            StdOut.println(value);
        }
    }
}