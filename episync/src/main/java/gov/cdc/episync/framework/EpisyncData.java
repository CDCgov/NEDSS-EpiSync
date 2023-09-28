package gov.cdc.episync.framework;

import java.util.Dictionary;

public interface EpisyncData<K, V> {
    Dictionary<K, V> data();
    V get(K key);
    V put(K key, V value);
}
