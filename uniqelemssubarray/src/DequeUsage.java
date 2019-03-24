import java.util.*;

/*
1. Read arrSize to deque void init(Deque d, int arrSize, InputStream input)
2. read all content to set, size => number of unique elements int evaluateUniqueness(Deque d),
3. add result to sorted collection: TreeSet.
4. remove head add tail to deck, repeat step 2
5. Take head from treeset => this is your result
*/
public class DequeUsage {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int totalNumber = in.nextInt();
        int arraySize = in.nextInt();
        Deque<Integer> deque = new ArrayDeque<>(arraySize);
        SortedSet<Integer> results = new TreeSet<>();

        // take deque to init state
        for (int i = 0; i < arraySize; i++) {
            deque.addLast(in.nextInt());
        }

        results.add(calculateUniqElements(deque));

        // now roll over the array checking uniq
        for (int i = 0; i < totalNumber - arraySize; i++) {
            deque.removeFirst();
            deque.addLast(in.nextInt());
            results.add(calculateUniqElements(deque));
        }

        System.out.println(results.last());
    }

    private static Map<ArrayHolder<Integer>, Integer> cache = new HashMap<>();

    private static int calculateUniqElements(Deque<Integer> deque) {

        Integer result = null;
        ArrayHolder<Integer> valueHolder = new ArrayHolder<>(deque);
        Integer cachedValue = cache.get(valueHolder);

        if (cachedValue != null) {
            result = cachedValue;
        } else {
            Set<Integer> buffer = new HashSet<>(deque);
            result = buffer.size();
            cache.put(valueHolder, result);
        }

        return result;
    }

    private static class ArrayHolder<T> {
        private T[] elements;

        public ArrayHolder(Collection<T> collection) {
            elements = (T[]) collection.toArray();
        }

        public T[] getElements() {
            return elements.clone();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ArrayHolder<?> that = (ArrayHolder<?>) o;
            return Arrays.equals(elements, that.elements);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(elements);
        }
    }
}

    /*

    Concurrent solution works even slower than sequential, probably hackerrank uses only one processor....

    public class DequeUsage {
        private static SortedSet<Integer> results = new TreeSet<>();
        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            int totalNumber = in.nextInt();
            int arraySize = in.nextInt();
            Deque<Integer> deque = new ArrayDeque<>(arraySize);


            // take deque to init state
            for (int i = 0; i < arraySize; i++) {
                deque.addLast(in.nextInt());
            }

            addToResults(calculateUniqElements(deque));

            CountDownLatch latch = new CountDownLatch(totalNumber - arraySize);
            // now roll over the array checking uniq
            for (int i = 0; i < totalNumber - arraySize; i++) {
                deque.removeFirst();
                deque.addLast(in.nextInt());

                CompletableFuture<Void> adding = CompletableFuture
                    .supplyAsync(() -> calculateUniqElements(deque))
                    .thenAccept((r) -> { addToResults(r); })
                    .thenRun(() -> { latch.countDown(); });
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(results.last());
        }

        private static void addToResults(Integer result) {
            synchronized (results) {
                results.add(result);
            }
        }

        public static int calculateUniqElements(Deque<Integer> deque) {
            Set<Integer> buffer = new HashSet<>();
            buffer.addAll(deque);
            int result = buffer.size();
            return result;
        }
    }

    */
