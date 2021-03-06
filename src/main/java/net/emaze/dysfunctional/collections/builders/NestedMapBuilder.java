package net.emaze.dysfunctional.collections.builders;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import net.emaze.dysfunctional.contracts.dbc;
import java.util.function.Supplier;

/**
 * A builder for nested maps.
 *
 * @param <K> the map key type
 * @author rferranti
 */
public class NestedMapBuilder<K> {

    private final Deque<K> stack = new LinkedList<K>();
    private final Supplier<Map<K, Object>> supplier;
    private final Map<K, Object> buildee;
    public final NestedMapBuilder<K> ___;

    public NestedMapBuilder(Supplier<Map<K, Object>> supplier) {
        dbc.precondition(supplier != null, "cannot create a MapTreeBuilder with a null map supplier");
        this.supplier = supplier;
        this.buildee = supplier.get();
        this.___ = this;
    }

    public NestedMapBuilder<K> add(Map<K, ? extends Object> map) {
        dbc.precondition(map != null, "cannot merge a null map");
        current().putAll(map);
        return this;
    }

    public NestedMapBuilder<K> add(K key, Object value) {
        current().put(key, value);
        return this;
    }

    public NestedMapBuilder<K> push(K key) {
        current().put(key, supplier.get());
        stack.addLast(key);
        return this;
    }

    public NestedMapBuilder<K> pop() {
        dbc.precondition(stack.size() != 0, "popping from an empty stack");
        stack.removeLast();
        return this;
    }

    public Map<K, Object> toMap() {
        return buildee;
    }
    
    public Map<K, Object> toUnmodifiableMap() {
        return Collections.unmodifiableMap(buildee);
    }

    private Map<K, Object> current() {
        Map<K, Object> current = this.buildee;
        for (K nested : stack) {
            current = (Map<K, Object>) current.get(nested);
        }
        return current;
    }
}
