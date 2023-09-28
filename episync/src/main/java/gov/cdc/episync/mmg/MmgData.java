package gov.cdc.episync.mmg;

import gov.cdc.episync.framework.EpisyncData;
import lombok.Data;

import java.util.Dictionary;
import java.util.Hashtable;

@Data
public class MmgData<K, V> implements EpisyncData<K, V> {
    private Hashtable<K, V> data = new Hashtable<>();

    @Override
    public Dictionary<K, V> data() {
        return data;
    }

    @Override
    public V get(K key) {
        return data.get(key);
    }

    @Override
    public V put(K key, V value) {
        return data.put(key, value);
    }
}
