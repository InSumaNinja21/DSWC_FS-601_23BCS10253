package Day3.Q4;

import java.util.LinkedHashMap;
import java.util.Map;

// LRU Cache Class
class VideoCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public VideoCache(int capacity) {

        // initialCapacity, loadFactor, accessOrder
        super(capacity, 0.75f, true);

        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {

        return size() > capacity;
    }
}

public class StreamFlixCache {

    public static void main(String[] args) {

        VideoCache<Integer, String> cache =
                new VideoCache<>(3);

        cache.put(101, "Inception");
        cache.put(102, "Interstellar");
        cache.put(103, "The Dark Knight");

        System.out.println("Initial Cache:");
        System.out.println(cache);

        // Access movie 101
        cache.get(101);

        System.out.println("\nAfter accessing movie 101:");
        System.out.println(cache);

        // Insert new movie
        cache.put(104, "Oppenheimer");

        System.out.println("\nAfter adding movie 104:");
        System.out.println(cache);

        // Access movie 103
        cache.get(103);

        // Insert another movie
        cache.put(105, "Avatar");

        System.out.println("\nAfter adding movie 105:");
        System.out.println(cache);
    }
}
