package gov.cdc.episync.framework;

import java.util.Dictionary;

public interface EpisyncData<K, V> {
    Dictionary<K, V> data();
}
