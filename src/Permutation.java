import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public final class Permutation {
    private Permutation() {
    }

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

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        int n = 0;
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            n++;

            if (n > k) {
                if (StdRandom.uniform(n) >= k) {
                    continue;
                }
                queue.dequeue();
            }

            queue.enqueue(string);
        }

        for (int i = 0; i < k; i++) {
            String value = queue.dequeue();
            StdOut.println(value);
        }
    }
}
