package net.emaze.dysfunctional.collections;

import java.util.HashMap;
import net.emaze.dysfunctional.dispatching.delegates.Provider;

/**
 * Creates an empty HashMap.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @author rferranti
 */
public class HashMapFactory<K, V> implements Provider<HashMap<K, V>> {

    @Override
    public HashMap<K, V> provide() {
        return new HashMap<K, V>();
    }
}