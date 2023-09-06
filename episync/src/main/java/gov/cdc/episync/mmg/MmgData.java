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
}
