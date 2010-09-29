package net.emaze.dysfunctional.iterations;

import java.util.Iterator;

/**
 * An infinite Iterator always yields the same value
 * @param <T> 
 * @author rferranti
 */
public class ConstantIterator<T> implements Iterator<T> {

    private final T value;

    public ConstantIterator(T value) {
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        return value;
    }

    /**
     * Ignore semantic (vs fail).
     * Removing from a infinite constant iterator just `removes` one element
     * and leaves everything exactly as it was.
     */
    @Override
    public void remove() {
        
    }
}
